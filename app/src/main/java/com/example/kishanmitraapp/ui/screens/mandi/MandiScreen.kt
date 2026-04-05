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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kishanmitraapp.data.model.MspPrice
import com.example.kishanmitraapp.ui.theme.*
import com.example.kishanmitraapp.viewmodel.MspViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MandiScreen(
    onBackClick: () -> Unit,
    viewModel: MspViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val mspData = viewModel.mspData
    val isLoading = viewModel.isLoading
    val errorMsg = viewModel.errorMsg

    LaunchedEffect(Unit) {
        viewModel.fetchMspPrices()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MSP Prices", fontWeight = FontWeight.Bold) },
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
            // Header with Search
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
                        Text("Government MSP Rates", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    
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

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = GreenPrimary)
                }
            } else if (errorMsg != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = errorMsg ?: "Unknown error", color = Color.Red)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val filteredPrices = mspData?.prices?.filter { 
                        it.crop.contains(searchQuery, ignoreCase = true) 
                    } ?: emptyList()
                    
                    items(filteredPrices) { item ->
                        MspPriceCard(item)
                    }
                }
            }
        }
    }
}

@Composable
fun MspPriceCard(mspPrice: MspPrice) {
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
                    Text(mspPrice.crop, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                    Text("${mspPrice.season} | ${mspPrice.year}", fontSize = 12.sp, color = TextSecondary)
                }
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text("₹${mspPrice.mspPerQuintal.toInt()}", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = GreenDark)
                Text(mspPrice.unit, fontSize = 11.sp, color = TextSecondary)
            }
        }
    }
}
