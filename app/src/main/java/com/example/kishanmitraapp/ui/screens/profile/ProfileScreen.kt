package com.example.kishanmitraapp.ui.screens.profile

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kishanmitraapp.ui.theme.*
import com.example.kishanmitraapp.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val userProfile = viewModel.userProfile
    val context = LocalContext.current

    // Fetch profile on enter
    LaunchedEffect(Unit) {
        viewModel.getProfile()
    }

    // Handle error messages
    LaunchedEffect(viewModel.errorMsg) {
        viewModel.errorMsg?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.errorMsg = null
        }
    }

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
            CanvasIcons()

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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
                    text = userProfile?.fullName ?: "Farmer",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = userProfile?.email ?: "Digital Farming Partner",
                    color = WaterFoam.copy(alpha = 0.9f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (viewModel.isLoading && userProfile == null) {
            Box(modifier = Modifier.fillMaxWidth().height(200.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = WaterBlueDeep)
            }
        } else {
            // Profile Details
            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                Text(
                    "Farm Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = WaterBlueDeep
                )

                Spacer(modifier = Modifier.height(12.dp))

                WaterInfoCard(
                    items = listOf(
                        ProfileInfoItem("Full Name", userProfile?.fullName ?: "N/A", Icons.Default.Badge),
                        ProfileInfoItem("Phone Number", userProfile?.phoneNumber ?: "N/A", Icons.Default.Phone),
                        ProfileInfoItem("Irrigation", userProfile?.farmInfo?.irrigationMethod ?: "Not set", Icons.Default.Waves),
                        ProfileInfoItem("Soil Type", userProfile?.farmInfo?.soilType ?: "Not set", Icons.Default.Landscape),
                        ProfileInfoItem("Water Source", userProfile?.farmInfo?.waterSources?.joinToString(", ") ?: "Not set", Icons.Default.Water)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Account Preferences",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = WaterBlueDeep
                )

                Spacer(modifier = Modifier.height(12.dp))

                WaterSettingsList(
                    preferences = listOf(
                        "Water Savings Report" to (userProfile?.preferences?.waterSavingsReport ?: false),
                        "Weather Alerts" to (userProfile?.preferences?.weatherAlerts ?: false),
                        "Expert Consultation" to (userProfile?.preferences?.expertConsultation ?: false)
                    ),
                    onToggle = { key, value ->
                        val updates = mutableMapOf<String, Any>()
                        val currentPrefs = userProfile?.preferences ?: return@WaterSettingsList
                        
                        val newPrefs = when(key) {
                            "Water Savings Report" -> currentPrefs.copy(waterSavingsReport = value)
                            "Weather Alerts" -> currentPrefs.copy(weatherAlerts = value)
                            "Expert Consultation" -> currentPrefs.copy(expertConsultation = value)
                            else -> currentPrefs
                        }
                        updates["preferences"] = newPrefs
                        viewModel.updateProfile(updates)
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.logout(onLogout) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = WaterBlueDeep),
                    enabled = !viewModel.isLoading
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Icon(Icons.Default.Logout, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Logout", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
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
fun WaterSettingsList(
    preferences: List<Pair<String, Boolean>>,
    onToggle: (String, Boolean) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        preferences.forEach { (title, isEnabled) ->
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                color = Color.White,
                shadowElevation = 2.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        if (isEnabled) Icons.Default.CheckCircle else Icons.Default.Cancel, 
                        contentDescription = null, 
                        tint = if (isEnabled) GreenPrimary else Color.Gray, 
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(title, modifier = Modifier.weight(1f), fontSize = 15.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
                    Switch(
                        checked = isEnabled, 
                        onCheckedChange = { onToggle(title, it) },
                        colors = SwitchDefaults.colors(checkedThumbColor = WaterBlueDeep)
                    )
                }
            }
        }
    }
}

data class ProfileInfoItem(val label: String, val value: String, val icon: ImageVector)
