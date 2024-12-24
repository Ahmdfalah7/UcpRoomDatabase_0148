package com.example.ucp2_148.ui.view.suplier

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_148.ui.viewmodel.PenyediaViewModel
import com.example.ucp2_148.ui.viewmodel.supplier.FormErrorState
import com.example.ucp2_148.ui.viewmodel.supplier.SplUIState
import com.example.ucp2_148.ui.viewmodel.supplier.SuplierEvent
import com.example.ucp2_148.ui.viewmodel.supplier.SuplierViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertSplView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SuplierViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.SpluiState
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
                judul = "Insert Suplier",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
            InsertBodySuplier(
                uiState = uiState,
                onValueChange = { updateEvent -> viewModel.updateState(updateEvent) },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData()
                    }
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodySuplier(
    modifier: Modifier = Modifier,
    onValueChange: (SuplierEvent) -> Unit,
    uiState: SplUIState,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormSuplier(
            suplierEvent = uiState.suplierEvent,
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
fun FormSuplier(
    suplierEvent: SuplierEvent = SuplierEvent(),
    onValueChange: (SuplierEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.idspl,
            onValueChange = {
                onValueChange(suplierEvent.copy(idspl = it))
            },
            label = { Text("ID Suplier") },
            isError = errorState.idspl != null,
            placeholder = { Text("Masukkan ID Suplier") },
            singleLine = true
        )
        Text(text = errorState.idspl ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.namaspl,
            onValueChange = {
                onValueChange(suplierEvent.copy(namaspl = it))
            },
            label = { Text("Nama Suplier") },
            isError = errorState.namaspl != null,
            placeholder = { Text("Masukkan nama suplier") },
            singleLine = true
        )
        Text(text = errorState.namaspl ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.kontak,
            onValueChange = {
                onValueChange(suplierEvent.copy(kontak = it))
            },
            label = { Text("Kontak") },
            isError = errorState.kontak != null,
            placeholder = { Text("Masukkan kontak suplier") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )
        Text(text = errorState.kontak ?: "", color = Color.Red)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.alamat,
            onValueChange = {
                onValueChange(suplierEvent.copy(alamat = it))
            },
            label = { Text("Alamat") },
            isError = errorState.alamat != null,
            placeholder = { Text("Masukkan alamat suplier") },
            singleLine = false
        )
        Text(text = errorState.alamat ?: "", color = Color.Red)
    }
}
