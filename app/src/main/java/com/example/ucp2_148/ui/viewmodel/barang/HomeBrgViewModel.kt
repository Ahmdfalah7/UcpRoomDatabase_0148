package com.example.ucp2_148.ui.viewmodel.barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_148.data.entity.Barang
import com.example.ucp2_148.data.repository.RepositoryBrg
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

class HomeBrgViewModel(
    private val repositoryBarang: RepositoryBrg
) : ViewModel() {

    val homeBrgUiState: StateFlow<HomeBrgUiState> = flow {
        emit(HomeBrgUiState(isLoading = true)) // Emit initial loading state
        delay(900) // Simulasi delay jika diperlukan
        val barangList = repositoryBarang.getAllBrg().firstOrNull() // Ambil data pertama
        emit(HomeBrgUiState(listBarang = barangList.orEmpty(), isLoading = false))
    }
        .catch { exception ->
            emit(
                HomeBrgUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = exception.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeBrgUiState(isLoading = true)
        )
}

data class HomeBrgUiState(
    val listBarang: List<Barang> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)
