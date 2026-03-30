package com.example.kishanmitraapp.ui.screens.profile

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kishanmitraapp.ui.theme.*

@Composable
fun ProfileScreen() {
    val scrollState = rememberScrollState()
    
    // Animation for "Flowing Water" effect
    val infiniteTransition = rememberInfiniteTransition(label = "waterFlow")
    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "waveOffset"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WaterFoam)
            .verticalScroll(scrollState)
    ) {
        // Water Flow Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(bottomStart = 50.dp, bottomEnd = 50.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(WaterBlueDeep, WaterBlueMid, WaterBlueLight)
                    )
                )
        ) {
            // Decorative "Bubbles" or "Waves" background
            CanvasIcons()

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Profile Image with a "Water Ripple" border
                Surface(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    color = Color.White.copy(alpha = 0.2f),
                    border = CardDefaults.outlinedCardBorder().copy(
                        width = 3.dp, 
                        brush = Brush.sweepGradient(listOf(WaterBlueLight, Color.White, WaterBlueLight))
                    )
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(70.dp).padding(15.dp),
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Narendra Modi",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Water Conservation Advocate",
                    color = WaterFoam.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile Details in "Bubbles" (Cards)
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Text(
                "Water Usage & Farm Info",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = WaterBlueDeep
            )

            Spacer(modifier = Modifier.height(12.dp))

            WaterInfoCard(
                items = listOf(
                    ProfileInfoItem("Irrigation Method", "Drip Irrigation", Icons.Default.Waves),
                    ProfileInfoItem("Water Source", "Borewell & Canal", Icons.Default.Water),
                    ProfileInfoItem("Soil Moisture", "Optimal (65%)", Icons.Default.Opacity)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Preferences",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = WaterBlueDeep
            )

            Spacer(modifier = Modifier.height(12.dp))

            WaterSettingsList()

            Spacer(modifier = Modifier.height(32.dp))

            // Blue Logout Button
            Button(
                onClick = { /* Logout */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = WaterBlueDeep)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Logout", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun CanvasIcons() {
    Box(modifier = Modifier.fillMaxSize()) {
        Icon(
            Icons.Default.WaterDrop,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.1f),
            modifier = Modifier.size(150.dp).align(Alignment.TopEnd).offset(x = 40.dp, y = (-20).dp)
        )
        Icon(
            Icons.Default.Waves,
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.1f),
            modifier = Modifier.size(200.dp).align(Alignment.BottomStart).offset(x = (-50).dp, y = 30.dp)
        )
    }
}

@Composable
fun WaterInfoCard(items: List<ProfileInfoItem>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            items.forEachIndexed { index, item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(WaterBlueLight.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(item.icon, contentDescription = null, tint = WaterBlueDeep, modifier = Modifier.size(24.dp))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(item.label, fontSize = 12.sp, color = TextSecondary)
                        Text(item.value, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = WaterBlueDeep)
                    }
                }
                if (index < items.size - 1) {
                    Divider(color = WaterFoam, thickness = 1.dp, modifier = Modifier.padding(start = 60.dp))
                }
            }
        }
    }
}

@Composable
fun WaterSettingsList() {
    val settings = listOf(
        Pair("Water Savings Report", Icons.Default.Assessment),
        Pair("Weather Alerts", Icons.Default.NotificationsActive),
        Pair("Expert Consultation", Icons.Default.SupportAgent),
        Pair("Language", Icons.Default.Language)
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        settings.forEach { (title, icon) ->
            Surface(
                onClick = { /* Navigate */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(icon, contentDescription = null, tint = WaterBlueMid, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(title, modifier = Modifier.weight(1f), fontSize = 15.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = WaterBlueLight)
                }
            }
        }
    }
}

data class ProfileInfoItem(val label: String, val value: String, val icon: ImageVector)
