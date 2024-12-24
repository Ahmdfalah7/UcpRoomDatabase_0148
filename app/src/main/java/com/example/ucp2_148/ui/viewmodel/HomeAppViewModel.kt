package com.example.ucp2_148.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2_148.data.entity.Barang
import com.example.ucp2_148.data.entity.Suplier
import com.example.ucp2_148.data.repository.RepositoryBrg
import com.example.ucp2_148.data.repository.RepositorySpl
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeAppViewModel(
    private val repositoryBrg: RepositoryBrg,
    private val repositorySpl: RepositorySpl
): ViewModel(){

    val homeBrgUiState: StateFlow<HomeBrgUiState> = repositoryBrg.getAllBrg()
        .filterNotNull()
        .map{
            HomeBrgUiState(
                barangList = it.toList(),
                isLoading = false,
            )
        }
        .catch {throwable ->
            emit(
                HomeBrgUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = throwable.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeBrgUiState(
                isLoading = true
            )
        )
    val homeSplUiState: StateFlow<HomeSplUiState> = repositorySpl.getAllSpl()
        .filterNotNull()
        .map{
            HomeSplUiState(
                listSuplier = it.toList(),
                isLoading = false,
            )
        }
        .catch { throwable ->
            emit(
                HomeSplUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = throwable.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeSplUiState(
                isLoading = true
            )
        )
}

data class HomeBrgUiState(
    val barangList: List<Barang> = listOf(),
    val isLoading: Boolean = false,
    val isError : Boolean = false,
    val errorMessage: String = ""
)

data class HomeSplUiState(
    val listSuplier: List<Suplier> = listOf(),
    val isLoading: Boolean = false,
    val isError : Boolean = false,
    val errorMessage: String = ""
)