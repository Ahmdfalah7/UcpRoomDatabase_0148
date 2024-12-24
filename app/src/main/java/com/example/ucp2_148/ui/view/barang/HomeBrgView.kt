package com.example.ucp2_148.ui.view.barang

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.ucp2_148.ui.viewmodel.barang.HomeBrgViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeBrgView(
    viewModel: HomeBrgViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddBarang: () -> Unit = { },
    onDetailBrgClick: (String) -> Unit = { },
    onBack: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Daftar Barang",
                showBackButton = false,
                onBack = { },
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBarang,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Barang",
                )
            }
        }
    ) { innerPadding ->
        val detailBrgUiState by viewModel.homeBrgUiState.collectAsState()

        BodyHomeBarangView(
            homeUiState = detailBrgUiState,
            onClick = {
                onDetailBrgClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeBarangView(
    homeUiState: com.example.ucp2_148.ui.viewmodel.barang.HomeBrgUiState,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    when {
        homeUiState.isLoading -> {
            // Menampilkan indikator loading
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        homeUiState.isError -> {
            // Menampilkan pesan error
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
        homeUiState.listBarang.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data barang.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            ListBarang(
                listBarang = homeUiState.listBarang,
                onClick = {
                    onClick(it)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListBarang(
    listBarang: List<Barang>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listBarang,
            itemContent = { barang ->
                CardBarang(
                    barang = barang,
                    onClick = { onClick(barang.idbrg) }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardBarang(
    barang: Barang,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    // Menentukan warna card berdasarkan stok
    val cardColor = when {
        barang.stok in 1..10 -> MaterialTheme.colorScheme.error // Warna merah untuk stok antara 1 dan 10
        barang.stok > 10 -> Color.Green // Warna hijau untuk stok lebih dari 10
        else -> MaterialTheme.colorScheme.surface // Warna default jika tidak memenuhi kondisi
    }

    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp), // Menambahkan bayangan untuk efek kedalaman
        colors = CardDefaults.cardColors(
            containerColor = cardColor // Menggunakan warna yang sudah ditentukan
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp) // Menambah padding untuk card
        ) {
            // Nama Barang
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Nama Barang",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = barang.namabrg,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // Deskripsi Barang
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Deskripsi",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = barang.deskripsi,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // Harga Barang
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Harga",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "Harga: Rp.${barang.harga}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            // Stok Barang
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Stok",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "Stok: ${barang.stok}",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
