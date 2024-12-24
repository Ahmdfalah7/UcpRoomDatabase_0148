package com.example.ucp2_148.ui.viewmodel.supplier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_148.data.entity.Suplier
import com.example.ucp2_148.data.repository.RepositorySpl
import kotlinx.coroutines.launch

class SuplierViewModel(private val repositorySpl: RepositorySpl) : ViewModel() {

    var SpluiState by mutableStateOf(SplUIState())

    fun updateState(suplierEvent: SuplierEvent) {
        SpluiState = SpluiState.copy(
            suplierEvent = suplierEvent,
        )
    }

    private fun validateFields(): Boolean {
        val event = SpluiState.suplierEvent
        val errorState = FormErrorState(
            idspl = if (event.idspl.isEmpty())  "Id Tidak Boleh Kosong" else null,
            namaspl = if (event.namaspl.isEmpty())  "Nama Tidak Boleh Kosong" else null,
            kontak = if (event.kontak.isEmpty()) "Kontak Tidak Boleh Kosong" else null,
            alamat = if (event.alamat.isEmpty())  "Alamat Tidak Boleh Kosong" else null
        )
        SpluiState = SpluiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {
        val currentEvent = SpluiState.suplierEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositorySpl.insertSpl(currentEvent.toSuplierEntity())
                    SpluiState = SpluiState.copy(
                        snackbarMessage = "Data Berhasil disimpan",
                        suplierEvent = SuplierEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    SpluiState = SpluiState.copy(
                        snackbarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            SpluiState = SpluiState.copy(
                snackbarMessage = "Input Tidak valid. Periksa kembali data anda."
            )
        }
    }

    fun resetSnackBarMessage() {
        SpluiState = SpluiState.copy(snackbarMessage = null)
    }
}

data class SplUIState(
    val suplierEvent: SuplierEvent = SuplierEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackbarMessage: String? = null
)

data class FormErrorState(
    val idspl: String? = null,
    val namaspl: String? = null,
    val kontak: String? = null,
    val alamat: String? = null,
) {
    fun isValid(): Boolean {
        return idspl == null && namaspl == null && kontak == null && alamat == null
    }
}

fun SuplierEvent.toSuplierEntity(): Suplier = Suplier(
    idspl = idspl,
    namaspl = namaspl,
    kontak = kontak,
    alamat = alamat,
)

data class SuplierEvent(
    val idspl: String = "",
    val namaspl: String = "",
    val kontak: String = "",
    val alamat: String = "",
)
