package com.example.ucp2_148.ui.viewmodel.supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_148.data.entity.Suplier
import com.example.ucp2_148.data.repository.RepositorySpl
import com.example.ucp2_148.ui.viewmodel.HomeSplUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeSplViewModel(
    private val repositorySuplier: RepositorySpl
) : ViewModel() {
    val homeSplUiState: StateFlow<HomeSplUiState> = repositorySuplier.getAllSpl()
        .filterNotNull()
        .map {
            HomeSplUiState(
                listSuplier = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeSplUiState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeSplUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeSplUiState(
                isLoading = true,
            )
        )
}

data class HomeSplUiState(
    val listSuplier: List<Suplier> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)