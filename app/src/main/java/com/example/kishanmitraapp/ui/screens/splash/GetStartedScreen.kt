package com.example.kishanmitraapp.ui.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kishanmitraapp.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

data class AppService(
    val title: String,
    val icon: ImageVector,
    val color: Color
)

@Composable
fun GetStartedScreen(onGetStartedClick: () -> Unit) {
    val services = remember {
        listOf(
            AppService("AI Chatbot", Icons.Default.SmartToy, GreenPrimary),
            AppService("Weather", Icons.Default.WbSunny, Color(0xFFFBC02D)),
            AppService("Mandi Prices", Icons.Default.Storefront, Color(0xFFD84315)),
            AppService("Disease Alert", Icons.Default.PestControl, Color(0xFFC62828)),
            AppService("Crop Suggest", Icons.Default.Eco, GreenSecondary),
            AppService("Govt Schemes", Icons.Default.AccountBalance, Color(0xFF6A1B9A))
        )
    }

    var visibleIconsCount by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        delay(800)
        for (i in 1..services.size) {
            delay(500)
            visibleIconsCount = i
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Enhanced Agriculture Aura Background (Earth Tones)
        AgriAuraBackground()

        // 2. Main Content
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Column(
                modifier = Modifier.padding(top = 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Kishan Mitra",
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "Your Digital Farming Partner",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // 3. Central Farmer Section with Orbiting Services
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Orbital Services
                services.forEachIndexed { index, service ->
                    val isVisible = index < visibleIconsCount
                    val angle = (index * (360f / services.size)).toDouble()
                    val radius = 140.dp

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = isVisible,
                            enter = scaleIn(animationSpec = tween(600, easing = overshootInterpolator.toEasing())) + fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            ServiceOrbitIcon(service, angle, radius)
                        }
                    }
                }

                // Central Successful Farmer Image
                CentralFarmerImage()
            }

            // 4. Bottom Welcome Card
            BottomWelcomeCard(onGetStartedClick)
        }
    }
}

@Composable
fun AgriAuraBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "agriAura")
    
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(15000, easing = LinearEasing), RepeatMode.Restart), label = "phase"
    )

    // Deep Earth Green / Soil Brown Background
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF1B3022))) {
        Canvas(modifier = Modifier.fillMaxSize().blur(100.dp)) {
            val w = size.width
            val h = size.height

            // Sunlight Blob (Gold/Yellow)
            drawCircle(
                brush = Brush.radialGradient(listOf(Color(0xFFFFD54F).copy(alpha = 0.6f), Color.Transparent)),
                radius = 700f,
                center = androidx.compose.ui.geometry.Offset(
                    x = w * 0.5f + (200 * cos(Math.toRadians(phase.toDouble()))).toFloat(),
                    y = h * 0.2f + (150 * sin(Math.toRadians(phase.toDouble()))).toFloat()
                )
            )

            // Lush Leaf Blob (Green)
            drawCircle(
                brush = Brush.radialGradient(listOf(Color(0xFF43A047).copy(alpha = 0.5f), Color.Transparent)),
                radius = 900f,
                center = androidx.compose.ui.geometry.Offset(
                    x = w * 0.2f + (250 * sin(Math.toRadians(phase.toDouble() * 0.7))).toFloat(),
                    y = h * 0.6f + (200 * cos(Math.toRadians(phase.toDouble() * 0.7))).toFloat()
                )
            )

            // Rich Soil Blob (Brown)
            drawCircle(
                brush = Brush.radialGradient(listOf(Color(0xFF5D4037).copy(alpha = 0.6f), Color.Transparent)),
                radius = 800f,
                center = androidx.compose.ui.geometry.Offset(
                    x = w * 0.8f + (150 * cos(Math.toRadians(phase.toDouble() * 0.4))).toFloat(),
                    y = h * 0.8f + (100 * sin(Math.toRadians(phase.toDouble() * 0.4))).toFloat()
                )
            )
        }
        
        // Subtle Noise/Grain texture overlay can be added here if needed, 
        // but blur(100.dp) gives a nice smooth liquid feel.
    }
}

@Composable
fun CentralFarmerImage() {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.06f,
        animationSpec = infiniteRepeatable(tween(2500, easing = FastOutSlowInEasing), RepeatMode.Reverse), label = ""
    )

    Box(
        modifier = Modifier
            .size(190.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.2f))
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().clip(CircleShape),
            color = Color.White,
            tonalElevation = 12.dp
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1560493676-04071c5f467b?q=80&w=500&auto=format&fit=crop",
                contentDescription = "Success Farmer",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        
        // Success Radial Glow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        listOf(Color.Transparent, Color(0xFF4CAF50).copy(alpha = 0.25f))
                    )
                )
        )
        
        // Gold Result Badge
        Surface(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-5).dp, y = 5.dp),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    Icons.Default.Eco,
                    contentDescription = null,
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun ServiceOrbitIcon(service: AppService, angle: Double, radius: androidx.compose.ui.unit.Dp) {
    val xOffset = (radius.value * cos(Math.toRadians(angle))).dp
    val yOffset = (radius.value * sin(Math.toRadians(angle))).dp

    Column(
        modifier = Modifier.offset(x = xOffset, y = yOffset),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier.size(60.dp),
            shape = CircleShape,
            color = Color.White,
            shadowElevation = 10.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = service.icon,
                    contentDescription = service.title,
                    tint = service.color,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
        Text(
            text = service.title,
            color = Color.White,
            fontSize = 11.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(top = 6.dp)
                .background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                .padding(horizontal = 4.dp, vertical = 2.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BottomWelcomeCard(onGetStartedClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.96f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Growing Prosperity",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF1B3022),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Harness the power of AI to transform your fields into gold. Every insight, every second.",
                fontSize = 15.sp,
                color = Color.DarkGray,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
            Spacer(modifier = Modifier.height(28.dp))
            Button(
                onClick = onGetStartedClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Enter Your Farm", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null)
                }
            }
        }
    }
}

private fun OvershootInterpolator.toEasing() = Easing { x ->
    getInterpolation(x)
}
private val overshootInterpolator = OvershootInterpolator(1.3f)
