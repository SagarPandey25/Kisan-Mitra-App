package com.example.kishanmitraapp.ui.screens.mandi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kishanmitraapp.ui.theme.*

data class MandiPrice(val crop: String, val price: String, val change: String, val isUp: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandiScreen(onBackClick: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    
    val prices = listOf(
        MandiPrice("Wheat (Gehu)", "₹2,275/q", "+₹25", true),
        MandiPrice("Rice (Chawal)", "₹3,100/q", "-₹10", false),
        MandiPrice("Cotton (Kapas)", "₹7,500/q", "+₹150", true),
        MandiPrice("Soybean", "₹4,850/q", "+₹40", true),
        MandiPrice("Mustard (Sarson)", "₹5,400/q", "-₹20", false),
        MandiPrice("Onion (Pyaj)", "₹2,100/q", "+₹300", true)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mandi Prices", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE8F5E9))
            )
        },
        containerColor = BackgroundLight
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Money Theme Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFF2E7D32), Color(0xFF1B5E20))
                        )
                    )
                    .padding(24.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Payments, contentDescription = null, tint = EarthYellow, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Today's Market Value", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search any crop...", color = Color.White.copy(alpha = 0.6f)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                            focusedBorderColor = Color.White,
                            cursorColor = Color.White,
                            containerColor = Color.White.copy(alpha = 0.1f)
                        ),
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // List of Prices
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val filteredPrices = if (searchQuery.isEmpty()) prices else prices.filter { it.crop.contains(searchQuery, ignoreCase = true) }
                
                items(filteredPrices) { item ->
                    MandiPriceCard(item)
                }
            }
        }
    }
}

@Composable
fun MandiPriceCard(mandiPrice: MandiPrice) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(GreenPrimary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.CurrencyRupee, contentDescription = null, tint = GreenPrimary)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(mandiPrice.crop, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                    Text("Per Quintal", fontSize = 12.sp, color = TextSecondary)
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(mandiPrice.price, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = GreenDark)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        if (mandiPrice.isUp) Icons.Default.TrendingUp else Icons.Default.TrendingDown,
                        contentDescription = null,
                        tint = if (mandiPrice.isUp) Color(0xFF43A047) else Color(0xFFE53935),
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        mandiPrice.change,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (mandiPrice.isUp) Color(0xFF43A047) else Color(0xFFE53935)
                    )
                }
            }
        }
    }
}
