package com.example.ucp2_148.ui.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_148.data.entity.Barang
import com.example.ucp2_148.data.repository.RepositoryBrg
import kotlinx.coroutines.launch

class BarangViewModel(private val repositoryBrg: RepositoryBrg) : ViewModel() {

    var barangUIState by mutableStateOf(BarangUIState())

    fun updateState(barangEvent: BarangEvent) {
        barangUIState = barangUIState.copy(
            barangEvent = barangEvent
        )
    }

    private fun validateFields(): FormErrorState {
        val event = barangUIState.barangEvent
        return FormErrorState(
            idbrg = if (event.idbrg.isEmpty()) "Id Tidak Boleh Kosong" else null,
            namabrg = if (event.namabrg.isEmpty()) "Nama Tidak Boleh Kosong" else null,
            deskripsi = if (event.deskripsi.isEmpty()) "Deskripsi Tidak Boleh Kosong" else null,
            harga = if (event.harga <= 0) "Harga Tidak Boleh Kosong" else null,
            stok = if (event.stok <= 0) "Stok Tidak Boleh Kosong" else null,
            namaspl = if (event.namaspl.isEmpty()) "Nama suplier tidak boleh kosong" else null
        )
    }

    private fun isEntryValid(): Boolean {
        val errorState = validateFields()
        barangUIState = barangUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = barangUIState.barangEvent

        if (isEntryValid()) {
            viewModelScope.launch {
                try {
                    repositoryBrg.insertBrg(currentEvent.toBarangEntity())
                    barangUIState = barangUIState.copy(
                        snackbarMessage = "Data Berhasil Disimpan",
                        barangEvent = BarangEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    barangUIState = barangUIState.copy(
                        snackbarMessage = "Data Gagal Disimpan: ${e.message}"
                    )
                }
            }
        } else {
            barangUIState = barangUIState.copy(
                snackbarMessage = "Input Tidak Valid. Periksa Kembali Data Anda."
            )
        }
    }

    fun resetSnackBarMessage() {
        barangUIState = barangUIState.copy(snackbarMessage = null)
    }
}

data class BarangUIState(
    val barangEvent: BarangEvent = BarangEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null
)

data class FormErrorState(
    val idbrg: String? = null,
    val namabrg: String? = null,
    val deskripsi: String? = null,
    val harga: String? = null,
    val stok: String? = null,
    val namaspl: String? = null
) {
    fun isValid(): Boolean {
        return idbrg == null && namabrg == null && deskripsi == null && harga == null && stok == null && namaspl == null
    }
}

fun BarangEvent.toBarangEntity(): Barang = Barang(
    idbrg = idbrg,
    namabrg = namabrg,
    deskripsi = deskripsi,
    harga = harga,
    stok = stok,
    namaspl = namaspl
)

data class BarangEvent(
    val idbrg: String = "",
    val namabrg: String = "",
    val deskripsi: String = "",
    val harga: Int = 0,
    val stok: Int = 0,
    val namaspl: String = "" // Tidak nullable
)
