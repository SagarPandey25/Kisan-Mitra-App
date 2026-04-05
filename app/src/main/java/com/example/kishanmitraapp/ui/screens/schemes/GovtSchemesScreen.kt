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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kishanmitraapp.data.model.Scheme
import com.example.kishanmitraapp.ui.theme.*
import com.example.kishanmitraapp.viewmodel.SchemesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GovtSchemesScreen(
    onBackClick: () -> Unit,
    viewModel: SchemesViewModel = viewModel()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Income Support", "Insurance", "Credit", "Irrigation", "Market Access", "Infrastructure", "Training")
    val categoryMap = listOf("income_support", "insurance", "credit", "irrigation", "market_access", "infrastructure", "training")

    val schemesData = viewModel.schemesData
    val isLoading = viewModel.isLoading
    val errorMsg = viewModel.errorMsg

    LaunchedEffect(selectedTab) {
        viewModel.fetchSchemes(categoryMap[selectedTab])
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sarkari Yojana", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = IndiaSaffron,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color(0xFFFFF8E1)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
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
                    Text("Central Government Initiatives", color = IndiaNavy.copy(alpha = 0.7f), fontSize = 12.sp)
                }
            }

            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = IndiaNavy,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    if (selectedTab < tabPositions.size) {
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

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = IndiaSaffron)
                }
            } else if (errorMsg != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = errorMsg ?: "Error loading schemes", color = Color.Red)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(schemesData?.schemes ?: emptyList()) { scheme ->
                        SchemeCard(scheme)
                    }
                }
            }
        }
    }
}

@Composable
fun SchemeCard(scheme: Scheme) {
    val icon = when(scheme.category) {
        "income_support" -> Icons.Default.AccountBalanceWallet
        "insurance" -> Icons.Default.Security
        "credit" -> Icons.Default.CreditCard
        "irrigation" -> Icons.Default.WaterDrop
        else -> Icons.Default.Info
    }
    
    val color = when(scheme.category) {
        "income_support" -> IndiaSaffron
        "insurance" -> IndiaNavy
        "credit" -> IndiaGreen
        "irrigation" -> Color(0xFF2196F3)
        else -> EarthBrown
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = Color.White,
        shadowElevation = 3.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(scheme.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
                    Text(scheme.shortName, fontSize = 12.sp, color = color, fontWeight = FontWeight.Medium)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(scheme.description, fontSize = 14.sp, color = TextSecondary)
            
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text("Benefits: ", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                Text(scheme.benefits, fontSize = 13.sp, color = TextSecondary)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = { /* Open applyUrl */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = IndiaGreen),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Apply Now", color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
            }
        }
    }
}
