package com.example.ucp2_148.ui.viewmodel.barang


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_148.data.entity.Barang
import com.example.ucp2_148.data.repository.RepositoryBrg
import com.example.ucp2_148.ui.navigasi.DestinasiDetailBrg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailBrgViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryBrg: RepositoryBrg,
) : ViewModel() {
    private val BrgId: String = checkNotNull(savedStateHandle[DestinasiDetailBrg.idBrg])

    val detailUiState: StateFlow<DetailUiState> = repositoryBrg.getBrg(BrgId)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            )
        )

    fun deleteBrg() {
        detailUiState.value.detailUiEvent.toBarangEntity().let {
            viewModelScope.launch {
                repositoryBrg.deleteBrg(it)
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: BarangEvent = BarangEvent(),
    val isLoading:Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUieventEmpty: Boolean
        get() = detailUiEvent == BarangEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != BarangEvent()
}


fun Barang.toDetailUiEvent(): BarangEvent{
    return BarangEvent(
        idbrg = idbrg,
        namabrg = namabrg,
        deskripsi = deskripsi,
        harga = harga,
        stok = stok,
        namaspl = namaspl
    )
}
