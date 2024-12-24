package com.example.ucp2_148.ui.view.suplier

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2_148.data.entity.Suplier
import com.example.ucp2_148.ui.customwidget.TopAppBar
import com.example.ucp2_148.ui.viewmodel.HomeSplUiState
import com.example.ucp2_148.ui.viewmodel.PenyediaViewModel
import com.example.ucp2_148.ui.viewmodel.supplier.HomeSplViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeSplView(
    viewModel: HomeSplViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddSuplier: () -> Unit = { },
    onDetailClick: (String) -> Unit = { },
    onBack: () -> Unit = { },
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Daftar Suplier",
                showBackButton = false,
                onBack = { },
                modifier = modifier
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddSuplier,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Suplier",
                )
            }
        }
    ) { innerPadding ->
        val homeUiState by viewModel.homeSplUiState.collectAsState()

        BodyHomeSuplierView(
            homeUiState = homeUiState ,
            onClick = {
                onDetailClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyHomeSuplierView(
    homeUiState: HomeSplUiState,
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
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }
        homeUiState.listSuplier.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data suplier.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            ListSuplier(
                listSuplier = homeUiState.listSuplier,
                onClick = {
                    onClick(it)
                },
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListSuplier(
    listSuplier: List<Suplier>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listSuplier,
            itemContent = { suplier ->
                CardSuplier(
                    suplier = suplier,
                    onClick = { onClick(suplier.idspl) }
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSuplier(
    suplier: Suplier,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = suplier.namaspl, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Call,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = suplier.kontak, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Text(text = suplier.alamat, fontWeight = FontWeight.Bold)
            }
        }
    }
}
