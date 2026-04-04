package com.example.kishanmitraapp.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kishanmitraapp.data.model.*
import com.example.kishanmitraapp.ui.theme.*
import com.example.kishanmitraapp.utils.AppLanguage
import com.example.kishanmitraapp.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit,
    onLanguageSelected: (AppLanguage) -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedLanguage by remember { mutableStateOf(AppLanguage.ENGLISH) }
    var expandedLanguage by remember { mutableStateOf(false) }

    // Farm Info
    var irrigationMethod by remember { mutableStateOf("Drip") }
    var expandedIrrigation by remember { mutableStateOf(false) }
    val waterSourcesOptions = listOf("Borewell", "Canal", "Rainwater", "River", "Pond", "DugWell")
    val selectedWaterSources = remember { mutableStateListOf<String>() }

    // Preferences
    var waterSavingsReport by remember { mutableStateOf(true) }
    var weatherAlerts by remember { mutableStateOf(true) }
    var expertConsultation by remember { mutableStateOf(false) }

    // Dialog state
    var showSuccessDialog by remember { mutableStateOf(false) }

    val registerResult = viewModel.registerResult

    LaunchedEffect(registerResult) {
        registerResult?.let { result ->
            result.onSuccess {
                showSuccessDialog = true
            }.onFailure {
                Toast.makeText(context, it.message ?: "Registration failed", Toast.LENGTH_LONG).show()
                viewModel.registerResult = null
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { /* Don't dismiss by clicking outside */ },
            title = {
                Text(
                    text = "Registration Successful! 🎉",
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )
            },
            text = {
                Text(text = "You have successfully registered in Kisan App. Welcome to the community!")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onRegisterSuccess()
                        showSuccessDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                ) {
                    Text("Go to Home", color = Color.White)
                }
            },
            shape = RoundedCornerShape(24.dp),
            containerColor = Color.White
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(SoilDeep, ManureBrown, WheatGold)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(vertical = 32.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White.copy(alpha = 0.95f))
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Join Kishan Mitra",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = ManureDark
            )
            
            Text(
                text = "Set up your smart farm profile",
                fontSize = 14.sp,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Personal Info Section
            SectionHeader("Personal Details", Icons.Default.Person)
            
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null, tint = ManureBrown) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ManureBrown,
                    focusedLabelColor = ManureBrown
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = ManureBrown) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ManureBrown,
                    focusedLabelColor = ManureBrown
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null, tint = ManureBrown) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ManureBrown,
                    focusedLabelColor = ManureBrown
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = ManureBrown) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ManureBrown,
                    focusedLabelColor = ManureBrown
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Language Selection
            ExposedDropdownMenuBox(
                expanded = expandedLanguage,
                onExpandedChange = { expandedLanguage = !expandedLanguage },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedLanguage.label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Preferred Language") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLanguage) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Language, contentDescription = null, tint = ManureBrown) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ManureBrown,
                        focusedLabelColor = ManureBrown
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedLanguage,
                    onDismissRequest = { expandedLanguage = false }
                ) {
                    AppLanguage.entries.forEach { language ->
                        DropdownMenuItem(
                            text = { Text(language.label) },
                            onClick = {
                                selectedLanguage = language
                                expandedLanguage = false
                                onLanguageSelected(language)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Farm Info Section
            SectionHeader("Farm Information", Icons.Default.Agriculture)

            // Irrigation Method
            ExposedDropdownMenuBox(
                expanded = expandedIrrigation,
                onExpandedChange = { expandedIrrigation = !expandedIrrigation },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = irrigationMethod,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Irrigation Method") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedIrrigation) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Default.Waves, contentDescription = null, tint = ManureBrown) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ManureBrown,
                        focusedLabelColor = ManureBrown
                    )
                )
                ExposedDropdownMenu(
                    expanded = expandedIrrigation,
                    onDismissRequest = { expandedIrrigation = false }
                ) {
                    listOf("Drip", "Sprinkler", "FloodFurrow", "Surface", "SubSurface", "Manual").forEach { method ->
                        DropdownMenuItem(
                            text = { Text(method) },
                            onClick = {
                                irrigationMethod = method
                                expandedIrrigation = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Water Sources (Multiple choice)
            Text("Water Sources", modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = ManureBrown)
            waterSourcesOptions.forEach { source ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().clickable { 
                        if (selectedWaterSources.contains(source)) selectedWaterSources.remove(source) else selectedWaterSources.add(source)
                    }
                ) {
                    Checkbox(
                        checked = selectedWaterSources.contains(source),
                        onCheckedChange = { if (it) selectedWaterSources.add(source) else selectedWaterSources.remove(source) },
                        colors = CheckboxDefaults.colors(checkedColor = ManureBrown)
                    )
                    Text(source, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Preferences Section
            SectionHeader("Account Preferences", Icons.Default.Settings)
            
            PreferenceSwitch("Water Savings Report", waterSavingsReport) { waterSavingsReport = it }
            PreferenceSwitch("Weather Alerts", weatherAlerts) { weatherAlerts = it }
            PreferenceSwitch("Expert Consultation", expertConsultation) { expertConsultation = it }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (name.isBlank() || email.isBlank() || phone.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Please fill all required details", Toast.LENGTH_SHORT).show()
                    } else {
                        val request = RegistrationRequest(
                            fullName = name,
                            email = email,
                            phoneNumber = phone,
                            password = password,
                            preferredLanguage = selectedLanguage.name.lowercase().replaceFirstChar { it.uppercase() },
                            farmInfo = FarmInfo(
                                irrigationMethod = irrigationMethod,
                                waterSources = selectedWaterSources.toList(),
                                soilType = "Alluvial",
                                landAreaAcres = 0.0,
                                preferredSeasons = emptyList()
                            ),
                            preferences = UserPreferences(
                                waterSavingsReport = waterSavingsReport,
                                weatherAlerts = weatherAlerts,
                                expertConsultation = expertConsultation
                            )
                        )
                        viewModel.register(request)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ManureDark),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Register Now", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an account?", fontSize = 14.sp, color = TextSecondary)
                TextButton(onClick = onLoginClick) {
                    Text("Login", color = ManureBrown, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, icon: ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = ManureBrown, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = title, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, color = ManureDark)
    }
    Divider(modifier = Modifier.padding(bottom = 16.dp), color = ManureBrown.copy(alpha = 0.2f))
}

@Composable
fun PreferenceSwitch(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 14.sp, color = TextPrimary)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = ManureBrown,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color.LightGray
            )
        )
    }
}
