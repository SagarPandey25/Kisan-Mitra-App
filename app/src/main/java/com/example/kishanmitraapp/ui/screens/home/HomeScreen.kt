package com.example.kishanmitraapp.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.kishanmitraapp.utils.Translations
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onChatClick: () -> Unit,
    onWeatherClick: () -> Unit,
    onMandiClick: () -> Unit,
    onCropClick: () -> Unit,
    onDiseaseClick: () -> Unit,
    onSchemesClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val features = listOf(
        HomeFeature(Translations.getString("chatbot"), Icons.Default.Chat, EarthYellow, onChatClick),
        HomeFeature(Translations.getString("weather"), Icons.Default.WbSunny, Color(0xFF4FC3F7), onWeatherClick),
        HomeFeature(Translations.getString("mandi"), Icons.Default.Storefront, GreenSecondary, onMandiClick),
        HomeFeature(Translations.getString("crop_suggest"), Icons.Default.Agriculture, EarthBrown, onCropClick),
        HomeFeature(Translations.getString("disease_alert"), Icons.Default.BugReport, Color(0xFFE57373), onDiseaseClick),
        HomeFeature(Translations.getString("govt_schemes"), Icons.Default.AccountBalance, Color(0xFF9575CD), onSchemesClick)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Agriculture,
                            contentDescription = null,
                            tint = GreenPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Kisan Mitra",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = GreenPrimary,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Notifications */ }) {
                        Icon(Icons.Outlined.Notifications, contentDescription = "Notifications", tint = TextSecondary)
                    }
                    IconButton(
                        onClick = onProfileClick,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(GreenPrimary.copy(alpha = 0.1f), CircleShape)
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = GreenPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Search Bar
            SearchBarSection()

            Spacer(modifier = Modifier.height(16.dp))

            // Header Card with Auto-scrolling Image Carousel (14 Agriculture Images)
            HeaderSection()

            Spacer(modifier = Modifier.height(24.dp))

            // Professional Insight Section (Stats + Mandi)
            MarketInsightSection()

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = Translations.getString("services"),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary,
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        text = "View All",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = GreenPrimary,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable { /* View all services */ }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                FeaturesGrid(features)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Daily Tip Section
            DailyTipSection()
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SearchBarSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(Color.White)
            .clickable { /* Open Search */ }
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Outlined.Search, contentDescription = null, tint = TextSecondary)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Search for crops, mandi rates...",
                color = TextSecondary.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HeaderSection() {
    val headerImages = listOf(
        "https://images.unsplash.com/photo-1500382017468-9049fed747ef?q=80&w=1000&auto=format&fit=crop", // Field
        "https://images.unsplash.com/photo-1592982537447-7440770cbfc9?q=80&w=1000&auto=format&fit=crop", // Ploughing
        "https://images.unsplash.com/photo-1560493676-04071c5f467b?q=80&w=1000&auto=format&fit=crop", // Farming
        "https://images.unsplash.com/photo-1589923188900-85dae523342b?q=80&w=1000&auto=format&fit=crop", // Harvesting
        "https://images.unsplash.com/photo-1523348837708-15d4a09cfac2?q=80&w=1000&auto=format&fit=crop", // Plants
        "https://images.unsplash.com/photo-1625246333195-78d9c38ad449?q=80&w=1000&auto=format&fit=crop", // Farmer
        "https://images.unsplash.com/photo-1500937386664-56d1dfef3854?q=80&w=1000&auto=format&fit=crop", // Tractor
        "https://images.unsplash.com/photo-1524486361537-8ad15938e1a3?q=80&w=1000&auto=format&fit=crop", // Greenery
        "https://images.unsplash.com/photo-1563514227147-6d2ff665a6a0?q=80&w=1000&auto=format&fit=crop", // Irrigation
    )

    val pagerState = rememberPagerState(pageCount = { headerImages.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % headerImages.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    AsyncImage(
                        model = headerImages[page],
                        contentDescription = "Agriculture field image $page",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.85f))
                            )
                        )
                )

                Row(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 16.dp, end = 20.dp)
                ) {
                    repeat(headerImages.size) { iteration ->
                        val width = if (pagerState.currentPage == iteration) 12.dp else 4.dp
                        val color = if (pagerState.currentPage == iteration) WheatGold else Color.White.copy(alpha = 0.5f)
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 1.dp)
                                .clip(CircleShape)
                                .background(color)
                                .width(width)
                                .height(4.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 20.dp, bottom = 20.dp, end = 80.dp)
                ) {
                    Text(
                        text = Translations.getString("welcome"),
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        lineHeight = 28.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Empowering Indian Agriculture with Tech",
                        color = Color.White.copy(alpha = 0.9f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun MarketInsightSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Live Market & Weather",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                )
                Icon(Icons.Default.Refresh, contentDescription = null, tint = GreenPrimary, modifier = Modifier.size(18.dp))
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InsightItem(Icons.Default.WbSunny, "32°C", "Sunny", SkyBlue)
                Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.LightGray.copy(alpha = 0.3f))
                InsightItem(Icons.Default.TrendingUp, "₹2,275", "Wheat/q", GreenPrimary)
                Divider(modifier = Modifier.height(40.dp).width(1.dp), color = Color.LightGray.copy(alpha = 0.3f))
                InsightItem(Icons.Default.WaterDrop, "45%", "Humidity", Color(0xFF5C6BC0))
            }
        }
    }
}

@Composable
fun InsightItem(icon: ImageVector, value: String, label: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = TextPrimary)
        Text(label, fontSize = 11.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun FeaturesGrid(features: List<HomeFeature>) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        for (i in features.indices step 2) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FeatureCard(features[i], Modifier.weight(1f))
                if (i + 1 < features.size) {
                    FeatureCard(features[i + 1], Modifier.weight(1f))
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun FeatureCard(feature: HomeFeature, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .aspectRatio(1.1f)
            .clickable { feature.onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                imageVector = feature.icon,
                contentDescription = null,
                tint = feature.color.copy(alpha = 0.05f),
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = 10.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(feature.color.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = feature.icon,
                        contentDescription = feature.title,
                        tint = feature.color,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = feature.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun DailyTipSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = GreenPrimary.copy(alpha = 0.08f))
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(GreenPrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Lightbulb, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Farmer's Daily Tip",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = GreenPrimary
                )
                Text(
                    text = "Ensure proper soil moisture before sowing Rabi crops for better germination.",
                    fontSize = 13.sp,
                    color = TextPrimary.copy(alpha = 0.8f),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

data class HomeFeature(
    val title: String,
    val icon: ImageVector,
    val color: Color,
    val onClick: () -> Unit
)
