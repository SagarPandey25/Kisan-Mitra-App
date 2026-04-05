package com.example.kishanmitraapp.ui.screens.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(onBackClick: () -> Unit) {
    // Mock current season - Varsha (Monsoon) for demonstration
    val currentSeason = "Varsha (Monsoon)"
    val seasonGradient = listOf(SkyBlue, MonsoonGreen)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Weather Updates", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Nagpur, Maharashtra", fontSize = 12.sp, color = TextSecondary)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = BackgroundLight
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Hero Weather Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Brush.verticalGradient(seasonGradient))
                    .padding(24.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(currentSeason, color = Color.White, fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Icon(
                        Icons.Default.CloudQueue, 
                        contentDescription = null, 
                        tint = Color.White, 
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("28°C", color = Color.White, fontSize = 64.sp, fontWeight = FontWeight.Bold)
                    Text("Moderate Rain Expected", color = Color.White.copy(alpha = 0.9f), fontSize = 16.sp)
                }
            }

            // Weather Details Grid
            Text(
                "Agricultural Metrics",
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                WeatherDetailItem("Humidity", "82%", Icons.Default.WaterDrop, Color(0xFF4FC3F7), Modifier.weight(1f))
                WeatherDetailItem("Wind", "12 km/h", Icons.Default.Air, Color(0xFF90A4AE), Modifier.weight(1f))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                WeatherDetailItem("UV Index", "Low", Icons.Default.WbSunny, Color(0xFFFFB74D), Modifier.weight(1f))
                WeatherDetailItem("Rain", "4.2 mm", Icons.Default.Umbrella, Color(0xFF5C6BC0), Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Indian Season Forecast
            Text(
                "Seasonal Forecast",
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.height(140.dp)
            ) {
                val seasons = listOf(
                    SeasonItem("Vasanta", "Spring", "Mar-Apr", SummerYellow),
                    SeasonItem("Grishma", "Summer", "May-Jun", SunsetOrange),
                    SeasonItem("Varsha", "Monsoon", "Jul-Aug", MonsoonGreen),
                    SeasonItem("Sharad", "Autumn", "Sep-Oct", AutumnOrange),
                    SeasonItem("Hemanta", "Pre-Winter", "Nov-Dec", SkyBlue),
                    SeasonItem("Shishira", "Winter", "Jan-Feb", WinterBlue)
                )
                items(seasons) { season ->
                    SeasonCard(season)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Farmer Tip
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = MonsoonGreen.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Lightbulb, contentDescription = null, tint = EarthYellow)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Varsha Tip: Ensure proper drainage in soybean fields to prevent root rot during heavy rains.",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(label, fontSize = 12.sp, color = TextSecondary)
        }
    }
}

@Composable
fun SeasonCard(item: SeasonItem) {
    Surface(
        modifier = Modifier.width(100.dp).fillMaxHeight(),
        shape = RoundedCornerShape(16.dp),
        color = item.color.copy(alpha = 0.2f),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(item.color, Color.White)))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(item.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(item.englishName, fontSize = 10.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(item.months, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
    }
}

data class SeasonItem(val name: String, val englishName: String, val months: String, val color: Color)
