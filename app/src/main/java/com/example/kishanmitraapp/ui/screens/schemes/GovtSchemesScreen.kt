package com.example.kishanmitraapp.ui.screens.schemes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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

data class Scheme(
    val name: String,
    val type: String, // Central / State
    val benefit: String,
    val icon: ImageVector,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GovtSchemesScreen(onBackClick: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Central Govt", "State Govt", "Compensation")

    val schemes = listOf(
        Scheme("PM-Kisan Samman Nidhi", "Central", "₹6000/year direct benefit", Icons.Default.AccountBalanceWallet, IndiaSaffron),
        Scheme("PM Fasal Bima Yojana", "Central", "Crop insurance at low premium", Icons.Default.Security, IndiaNavy),
        Scheme("Kisan Credit Card (KCC)", "Central", "Low interest loans for farmers", Icons.Default.CreditCard, IndiaGreen),
        Scheme("Soil Health Card Scheme", "Central", "Testing & nutrients advice", Icons.Default.Science, EarthBrown),
        Scheme("Krishi Sinchayee Yojana", "State", "Subsidy on drip irrigation", Icons.Default.WaterDrop, Color(0xFF2196F3)),
        Scheme("Flood Relief Support", "Compensation", "Financial aid for crop loss", Icons.Default.Flood, SunsetRed)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sarkari Yojana", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = IndiaSaffron, titleContentColor = Color.White, navigationIconContentColor = Color.White)
            )
        },
        containerColor = Color(0xFFFFF8E1) // Soft Marigold background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Diversity Header (Tiranga Theme)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(
                        Brush.verticalGradient(
                            listOf(IndiaSaffron, IndiaWhite, IndiaGreen)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Empowering Every Farmer",
                        color = IndiaNavy,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                    Text("Central & State Government Initiatives", color = IndiaNavy.copy(alpha = 0.7f), fontSize = 12.sp)
                }
            }

            // Custom Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = IndiaNavy,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    if (selectedTab < tabPositions.size) {
                        // Use a basic Box as indicator for maximum compatibility across Material3 versions
                        Box(
                            Modifier
                                .tabIndicatorOffset(tabPositions[selectedTab])
                                .height(3.dp)
                                .background(IndiaSaffron)
                        )
                    }
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontWeight = FontWeight.Bold) }
                    )
                }
            }

            // Schemes List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val filteredSchemes = when (selectedTab) {
                    0 -> schemes.filter { it.type == "Central" }
                    1 -> schemes.filter { it.type == "State" }
                    else -> schemes.filter { it.type == "Compensation" }
                }

                items(filteredSchemes) { scheme ->
                    SchemeCard(scheme)
                }
            }
        }
    }
}

@Composable
fun SchemeCard(scheme: Scheme) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(scheme.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(scheme.icon, contentDescription = null, tint = scheme.color, modifier = Modifier.size(28.dp))
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(scheme.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(scheme.benefit, fontSize = 13.sp, color = TextSecondary)
            }
            
            IconButton(onClick = { /* View Info */ }) {
                Icon(Icons.Default.Info, contentDescription = "Details", tint = IndiaNavy)
            }
        }
    }
}
