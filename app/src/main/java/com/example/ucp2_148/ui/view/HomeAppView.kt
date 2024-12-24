package com.example.ucp2_148.ui.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2_148.ui.customwidget.TopHomeAppBar

@Composable
fun HomeAppView(
    onBarangClick: () -> Unit,
    onSuplierClick: () -> Unit,
    onAddBrgClick: () -> Unit,
    onAddSplClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopHomeAppBar(
                showBackButton = false,
                onBack = { },
            )
        },
        content = { paddingValues ->
            BodyHomeAppView(
                onBarangClick = { onBarangClick() },
                onSuplierClick = { onSuplierClick() },
                onAddBrgClick = { onAddBrgClick() },
                onAddSplClick = { onAddSplClick() },
                paddingValues = paddingValues,
            )
        }
    )
}

@Composable
fun BodyHomeAppView(
    onBarangClick: () -> Unit = { },
    onSuplierClick: () -> Unit = { },
    onAddBrgClick: () -> Unit = { },
    onAddSplClick: () -> Unit = { },
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        ElegantHorizontalCard(
            title = "Tambah Barang",
            action = onAddBrgClick,
            gradient = Brush.horizontalGradient(colors = listOf(Color(0xFF2196F3), Color(0xFF64B5F6))) // Gradasi biru
        )
        Spacer(modifier = Modifier.height(16.dp)) // Spacer antar kartu
        ElegantHorizontalCard(
            title = "List Barang",
            action = onBarangClick,
            gradient = Brush.horizontalGradient(colors = listOf(Color(0xFF1976D2), Color(0xFF42A5F5))) // Gradasi biru yang lebih gelap
        )
        Spacer(modifier = Modifier.height(16.dp)) // Spacer antar kartu
        ElegantHorizontalCard(
            title = "Tambah Suplier",
            action = onAddSplClick,
            gradient = Brush.horizontalGradient(colors = listOf(Color(0xFF1E88E5), Color(0xFF81D4FA))) // Gradasi biru lainnya
        )
        Spacer(modifier = Modifier.height(16.dp)) // Spacer antar kartu
        ElegantHorizontalCard(
            title = "List Suplier",
            action = onSuplierClick,
            gradient = Brush.horizontalGradient(colors = listOf(Color(0xFF1565C0), Color(0xFF64B5F6))) // Gradasi biru yang lebih cerah
        )
    }
}

@Composable
fun ElegantHorizontalCard(
    title: String,
    action: () -> Unit,
    gradient: Brush,
    modifier: Modifier = Modifier
) {
    var clicked by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.98f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    Card(
        modifier = modifier
            .fillMaxWidth() // Card memanjang penuh ke samping
            .padding(8.dp)
            .clickable {
                clicked = !clicked
                action()
            }
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .height(120.dp), // Height lebih kecil untuk kartu horizontal
        shape = RoundedCornerShape(16.dp), // Rounded corners
        elevation = CardDefaults.elevatedCardElevation(8.dp) // Slight shadow
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Menyusun secara horizontal
            ) {
                // Ikon di sebelah kiri
                Icon(
                    imageVector = if (title.contains("Tambah")) Icons.Filled.Add else Icons.Filled.List,
                    contentDescription = title,
                    modifier = Modifier.size(48.dp),
                    tint = Color.White
                )

                // Teks di sebelah kanan
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}
