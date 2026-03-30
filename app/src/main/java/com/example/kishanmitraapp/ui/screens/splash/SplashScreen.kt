package com.example.kishanmitraapp.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kishanmitraapp.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    val configuration = LocalConfiguration.current
    
    // Animation for the tractor moving across the screen
    val tractorX = remember { Animatable(-100f) }
    
    // Scale animation for the logo
    val scale = rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0.8f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(Unit) {
        // Start tractor animation - Slowed down to 5 seconds
        tractorX.animateTo(
            targetValue = configuration.screenWidthDp.toFloat() + 100f,
            animationSpec = tween(durationMillis = 5000, easing = LinearEasing)
        )
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(GreenDark, GreenPrimary, GreenSecondary)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Background Decorative Icons (Crop Theme)
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                Icons.Default.Eco,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.1f),
                modifier = Modifier.size(120.dp).align(Alignment.TopStart).offset(x = (-20).dp, y = 40.dp)
            )
            Icon(
                Icons.Default.Grain,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.1f),
                modifier = Modifier.size(100.dp).align(Alignment.BottomEnd).offset(x = 20.dp, y = (-40).dp)
            )
            Icon(
                Icons.Default.Grass,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.1f),
                modifier = Modifier.size(150.dp).align(Alignment.CenterStart).offset(x = (-50).dp)
            )
        }

        // Central Logo Area
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.scale(scale.value)
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f))
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Agriculture,
                    contentDescription = "App Logo",
                    tint = EarthYellow,
                    modifier = Modifier.size(100.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Kishan Mitra",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.headlineLarge
            )
            
            Text(
                text = "Empowering Indian Agriculture",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }

        // Animated Tractor and Ploughing Path
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        ) {
            // Ploughed Path (Soil line behind the tractor)
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(tractorX.value.coerceAtLeast(0f).dp)
                    .background(Brush.horizontalGradient(listOf(Color.Transparent, EarthBrown)))
                    .align(Alignment.CenterStart)
            )

            // The Tractor
            Icon(
                imageVector = Icons.Default.Agriculture,
                contentDescription = "Tractor",
                tint = EarthYellow,
                modifier = Modifier
                    .size(48.dp)
                    .offset(x = tractorX.value.dp)
            )
        }
        
        // Footer Version
        Text(
            text = "\"जय जवान, जय किसान\"",
            color = Color.White.copy(alpha = 0.5f),
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp),
            fontSize = 12.sp
        )
    }
}
