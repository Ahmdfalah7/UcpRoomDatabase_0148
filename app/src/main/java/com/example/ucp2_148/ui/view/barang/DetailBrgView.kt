package com.example.ucp2_148.ui.view.barang

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_148.data.entity.Barang
import com.example.ucp2_148.ui.customwidget.TopAppBar
import com.example.ucp2_148.ui.viewmodel.PenyediaViewModel
import com.example.ucp2_148.ui.viewmodel.barang.DetailBrgViewModel
import com.example.ucp2_148.ui.viewmodel.barang.DetailUiState
import com.example.ucp2_148.ui.viewmodel.barang.toBarangEntity

@Composable
fun DetailBrgView(
    modifier: Modifier = Modifier,
    viewModel: DetailBrgViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: () -> Unit = { },
    onEditClick: (String) -> Unit = { },
    onDeleteClick: () -> Unit = { }
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Detail Barang",
                showBackButton = true,
                onBack = onBack,
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailUiState.value.detailUiEvent.idbrg)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Barang"
                )
            }
        }
    ) { innerPadding ->
        val detailUiState by viewModel.detailUiState.collectAsState()

        BodyDetailBrg(
            modifier = Modifier.padding(innerPadding),
            detailUiState = detailUiState,
            onDeleteClick = {
                viewModel.deleteBrg()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BodyDetailBrg(
    modifier: Modifier = Modifier,
    detailUiState: DetailUiState = DetailUiState(),
    onDeleteClick: () -> Unit = { }
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator() // Tampilan Loading
            }
        }
        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailBrg(
                    barang = detailUiState.detailUiEvent.toBarangEntity(),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(16.dp)) // Spasi antara detail dan tombol
                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red, // Warna tombol delete
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Delete", fontSize = 18.sp)
                }

                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
        detailUiState.isUieventEmpty -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data Tidak Ditemukan",
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ItemDetailBrg(
    modifier: Modifier = Modifier,
    barang: Barang
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailBrg(title = "ID", content = barang.idbrg)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(title = "Nama", content = barang.namabrg)
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(title = "Harga", content = barang.harga.toString())

            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(title = "Stok", content = barang.stok.toString())
            Spacer(modifier = Modifier.padding(4.dp))

            ComponentDetailBrg(title = "Deskripsi", content = barang.deskripsi)
            Spacer(modifier = Modifier.padding(4.dp))
            Spacer(modifier = Modifier.height(4.dp))
            ComponentDetailBrg(title = "Nama Suplier", content = barang.namaspl)

        }
    }
}

@Composable
fun ComponentDetailBrg(
    modifier: Modifier = Modifier,
    title: String,
    content: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$title: ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = content,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Delete Data") },
        text = { Text("Apakah Anda Yakin ingin Menghapus Data?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Delete")
            }
        }
    )
}
