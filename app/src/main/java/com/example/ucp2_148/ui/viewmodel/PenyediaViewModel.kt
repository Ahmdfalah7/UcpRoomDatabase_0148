package com.example.ucp2_148.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2_148.TokoApp
import com.example.ucp2_148.ui.viewmodel.barang.BarangViewModel
import com.example.ucp2_148.ui.viewmodel.barang.DetailBrgViewModel
import com.example.ucp2_148.ui.viewmodel.barang.HomeBrgViewModel
import com.example.ucp2_148.ui.viewmodel.barang.UpdateBrgViewModel
import com.example.ucp2_148.ui.viewmodel.supplier.HomeSplViewModel
import com.example.ucp2_148.ui.viewmodel.supplier.SuplierViewModel

object PenyediaViewModel{

    val Factory = viewModelFactory {
        initializer {
            HomeAppViewModel(
                TokoApp().containerApp.repositoryBrg,
                TokoApp().containerApp.repositorySpl
            )
        }
        initializer {
            HomeBrgViewModel(
                TokoApp().containerApp.repositoryBrg
            )
        }
        initializer {
            HomeSplViewModel(
                TokoApp().containerApp.repositorySpl
            )
        }
        initializer {
            BarangViewModel(
                TokoApp().containerApp.repositoryBrg,
            )
        }
        initializer {
            DetailBrgViewModel(
                createSavedStateHandle(),
                TokoApp().containerApp.repositoryBrg,
            )
        }
        initializer {
            UpdateBrgViewModel(
                createSavedStateHandle(),
                TokoApp().containerApp.repositoryBrg,
            )
        }
        initializer {
            SuplierViewModel(
                TokoApp().containerApp.repositorySpl
            )
        }
    }
}

fun CreationExtras.TokoApp() : TokoApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TokoApp)