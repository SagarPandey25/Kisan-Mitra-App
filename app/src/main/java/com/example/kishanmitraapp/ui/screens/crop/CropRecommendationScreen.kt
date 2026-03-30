package com.example.kishanmitraapp.ui.screens.crop

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kishanmitraapp.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropRecommendationScreen(onBackClick: () -> Unit) {
    var landArea by remember { mutableStateOf("") }
    var selectedSeason by remember { mutableStateOf("Kharif") }
    var selectedSoilType by remember { mutableStateOf("Alluvial") }
    var showResult by remember { mutableStateOf(false) }

    val seasons = listOf("Kharif", "Rabi", "Zaid")
    val soilTypes = listOf("Alluvial", "Black", "Red", "Laterite", "Desert")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crop Suggestion", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Illustration Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(GreenSecondary, GreenPrimary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.Agriculture,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            "Smart Farming Plan",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Form Section
            Text(
                "Enter Land Details",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = landArea,
                onValueChange = { landArea = it },
                label = { Text("Land Area (in Acres)") },
                placeholder = { Text("e.g. 5") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Landscape, contentDescription = null, tint = GreenPrimary) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = GreenPrimary,
                    focusedLabelColor = GreenPrimary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Season Selection
            Text("Select Season", modifier = Modifier.fillMaxWidth(), fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                seasons.forEach { season ->
                    FilterChip(
                        selected = selectedSeason == season,
                        onClick = { selectedSeason = season },
                        label = { Text(season) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = GreenPrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Soil Type Selection
            Text("Soil Type", modifier = Modifier.fillMaxWidth(), fontSize = 14.sp, fontWeight = FontWeight.Medium)
            var expandedSoil by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandedSoil,
                onExpandedChange = { expandedSoil = !expandedSoil },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedSoilType,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSoil) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = GreenPrimary
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedSoil,
                    onDismissRequest = { expandedSoil = false }
                ) {
                    soilTypes.forEach { soil ->
                        DropdownMenuItem(
                            text = { Text(soil) },
                            onClick = {
                                selectedSoilType = soil
                                expandedSoil = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { showResult = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text("Get Suggestion", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            if (showResult) {
                Spacer(modifier = Modifier.height(24.dp))
                RecommendationCard(landArea, selectedSeason)
            }
        }
    }
}

@Composable
fun RecommendationCard(area: String, season: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Eco, contentDescription = null, tint = GreenSecondary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Recommended Crop", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (season == "Kharif") "Rice / Sugarcane" else "Wheat / Mustard",
                fontSize = 22.sp,
                color = GreenPrimary,
                fontWeight = FontWeight.ExtraBold
            )
            Divider(modifier = Modifier.padding(vertical = 12.dp))
            Text("Potential Yield for $area Acres:", fontSize = 14.sp, color = TextSecondary)
            Text("${area.toIntOrNull()?.times(15) ?: "---"} Quintals", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
