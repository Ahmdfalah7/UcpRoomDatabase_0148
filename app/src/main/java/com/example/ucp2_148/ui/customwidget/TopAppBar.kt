package com.example.ucp2_148.ui.customwidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2_148.R

@Composable
fun TopAppBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    judul: String,
    modifier: Modifier,
) {
    // Gradasi biru yang lebih elegan
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF1565C0), // Biru lebih gelap
            Color(0xFF42A5F5)  // Biru lebih cerah
        )
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient, shape = RoundedCornerShape(bottomEnd = 60.dp)) // Gradasi dengan sudut rounded
            .padding(horizontal = 22.dp, vertical = 26.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Tombol Kembali
            if (showBackButton) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)) // Background lebih transparan
                        .padding(6.dp) // Padding lebih besar agar lebih jelas
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            // Judul
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(6.dp)
            ) {
                Text(
                    text = judul,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp, // Ukuran font lebih besar
                )
            }

            // Logo
            Box(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)) // Background lebih transparan
                    .shadow(8.dp, CircleShape) // Menambahkan bayangan untuk kesan elegan
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            }
        }
    }
}
