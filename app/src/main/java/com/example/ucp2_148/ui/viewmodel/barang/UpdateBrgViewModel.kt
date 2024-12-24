package com.example.ucp2_148.ui.viewmodel.barang



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_148.data.entity.Barang
import com.example.ucp2_148.data.repository.RepositoryBrg
import com.example.ucp2_148.ui.navigasi.DestinasiUpdateBrg
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateBrgViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryBrg: RepositoryBrg
) : ViewModel() {
    var updateUIState by mutableStateOf(BarangUIState())
        private set
    private val brgId: String = checkNotNull(savedStateHandle[DestinasiUpdateBrg.idBrg])

    init {
        viewModelScope.launch {
            updateUIState = repositoryBrg.getBrg(brgId)
                .filterNotNull()
                .first()
                .toUIStateBrg()
        }
    }

    fun updateState(barangEvent: BarangEvent) {
        updateUIState = updateUIState.copy(
            barangEvent = barangEvent,
        )
    }

    fun validateField(): Boolean {
        val event = updateUIState.barangEvent
        val errorState = FormErrorState(
            idbrg = if (event.idbrg.isEmpty()) "ID tidak boleh kosong" else null,
            namabrg = if (event.namabrg.isEmpty())  "Nama tidak boleh kosong" else null,
            deskripsi =  if (event.deskripsi.isEmpty())  "Deskripsi tidak boleh kosong" else null,
            harga =  if (event.harga <= 0) "Harga tidak boleh kosong" else null,
            stok =  if (event.stok <= 0)  "Stok tidak boleh kosong" else null
        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData() {
        val currenEvent = updateUIState.barangEvent

        if (validateField()) {
            viewModelScope.launch {
                try {
                    repositoryBrg.updateBrg(currenEvent.toBarangEntity())
                    updateUIState = updateUIState.copy(
                        snackbarMessage = "Data berhasil diupdate",
                        barangEvent = BarangEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackbarMessage}")
                }catch (e: Exception) {
                    updateUIState = updateUIState.copy(
                        snackbarMessage = " Data gagal diupdate"
                    )
                }
            }
        } else {
            updateUIState = updateUIState.copy(
                snackbarMessage = "Data gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage() {
        updateUIState = updateUIState.copy(snackbarMessage = null)
    }
}

fun Barang.toUIStateBrg(): BarangUIState = BarangUIState(
    barangEvent = this.toDetailUiEvent(),
)