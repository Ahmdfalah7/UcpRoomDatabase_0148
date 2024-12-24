package com.example.ucp2_148.ui.customwidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
fun TopHomeAppBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    showImage: Boolean = true,
    imageResource: Int? = R.drawable.logo,
    modifier: Modifier = Modifier
) {
    // Gradasi biru yang lebih elegan
    val gradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF1565C0), // Biru lebih gelap
            Color(0xFF42A5F5)  // Biru lebih cerah
        )
    )

    Box(
        modifier = modifier
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
                    text = "B M W", // Nama singkat untuk perusahaan
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp, // Ukuran font lebih besar untuk kesan modern
                )
                Text(
                    text = "BMW COMPANY", // Nama lengkap perusahaan
                    color = Color.White.copy(alpha = 0.8f), // Sedikit transparan
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp, // Ukuran font sedikit lebih besar
                )
            }

            // Logo (jika diaktifkan)
            if (showImage && imageResource != null) {
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(60.dp) // Ukuran logo sedikit lebih kecil
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)) // Background lebih transparan
                        .shadow(8.dp, CircleShape) // Menambahkan bayangan untuk kesan elegan
                )
            }
        }
    }
}
