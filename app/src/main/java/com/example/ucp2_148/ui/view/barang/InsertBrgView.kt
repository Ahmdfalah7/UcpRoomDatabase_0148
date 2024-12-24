package com.example.ucp2_148.ui.view.barang

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_148.ui.customwidget.DropdownSpl
import com.example.ucp2_148.ui.viewmodel.PenyediaViewModel
import com.example.ucp2_148.ui.viewmodel.barang.BarangEvent
import com.example.ucp2_148.ui.viewmodel.barang.BarangUIState
import com.example.ucp2_148.ui.viewmodel.barang.BarangViewModel
import com.example.ucp2_148.ui.viewmodel.barang.FormErrorState
import kotlinx.coroutines.launch

@Composable
fun InsertBrgView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BarangViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.barangUIState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            com.example.ucp2_148.ui.customwidget.TopAppBar(
                judul = "Add Product",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
            InsertBodyBrg(
                uiState = uiState,
                onValueChange = { updateEvent -> viewModel.updateState(updateEvent) },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                        onNavigate()
                    }
                }
            )
        }
    }
}

@Composable
fun InsertBodyBrg(
    modifier: Modifier = Modifier,
    onValueChange: (BarangEvent) -> Unit,
    uiState: BarangUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormBarang(
            barangEvent = uiState.barangEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp)) // Spasi antara form dan tombol
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3), // Warna tombol
                contentColor = Color.White
            )
        ) {
            Text("Simpan", fontSize = 18.sp)
        }
    }
}

@Composable
fun FormBarang(
    barangEvent: BarangEvent = BarangEvent(),
    onValueChange: (BarangEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.idbrg,
            onValueChange = {
                onValueChange(barangEvent.copy(idbrg = it))
            },
            label = { Text("ID Barang") },
            isError = errorState.idbrg != null,
            placeholder = { Text("Masukkan ID Barang") },
            singleLine = true
        )
        Text(text = errorState.idbrg ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.namabrg,
            onValueChange = {
                onValueChange(barangEvent.copy(namabrg = it))
            },
            label = { Text("Nama") },
            isError = errorState.namabrg != null,
            placeholder = { Text("Masukkan nama barang") },
            singleLine = true
        )
        Text(text = errorState.namabrg ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.deskripsi,
            onValueChange = {
                onValueChange(barangEvent.copy(deskripsi = it))
            },
            label = { Text(text = "Deskripsi") },
            isError = errorState.deskripsi != null,
            placeholder = { Text(text = "Masukkan Deskripsi") },
            singleLine = false
        )
        Text(
            text = errorState.deskripsi ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.harga.toString(),
            onValueChange = {
                onValueChange(barangEvent.copy(harga = it.toIntOrNull() ?: 0))
            },
            label = { Text("Harga") },
            isError = errorState.harga != null,
            placeholder = { Text("Masukkan harga barang") },
            singleLine = true
        )
        Text(text = errorState.harga ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.stok.toString(),
            onValueChange = {
                onValueChange(barangEvent.copy(stok = it.toIntOrNull() ?: 0))
            },
            label = { Text("Stok") },
            isError = errorState.stok != null,
            placeholder = { Text("Masukkan stok barang") },
            singleLine = true
        )
        Text(text = errorState.stok ?: "", color = Color.Red)

        DropdownSpl(
            selectedValue = barangEvent.namaspl,
            options = listOf("imam", "fathur", "ais"), // Sesuaikan dengan data Anda
            label = "Nama Suplier",
            onValueChangedEvent = { selectedSupplier ->
                onValueChange(barangEvent.copy(namaspl = selectedSupplier))
            }
        )
        Text(text = errorState.namaspl ?: "", color = Color.Red)
    }
}
