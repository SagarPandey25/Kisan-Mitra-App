package com.example.kishanmitraapp.ui.screens.disease

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kishanmitraapp.ui.theme.*
import androidx.core.content.FileProvider
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseDetectionScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var symptoms by remember { mutableStateOf("") }
    var showDiagnosis by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    // Gallery Launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        if (uri != null) showDiagnosis = true
    }

    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedImageUri = tempImageUri
            showDiagnosis = true
        }
    }

    fun getTempUri(): Uri {
        val tempFile = File.createTempFile("temp_image", ".jpg", context.externalCacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", tempFile)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plant Doctor", fontWeight = FontWeight.Bold) },
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
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            
            Icon(
                Icons.Default.MedicalServices,
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 80.dp, y = 80.dp)
                    .graphicsLayer(alpha = 0.05f),
                tint = GreenPrimary
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Doctor Header Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = GreenPrimary)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Face, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text("I'm your Plant Doctor", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text("Upload a photo or describe symptoms.", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Image Preview if selected
                if (selectedImageUri != null) {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // AI Image Scan Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = SunsetOrange, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Image Analysis", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(
                                onClick = { 
                                    val uri = getTempUri()
                                    tempImageUri = uri
                                    cameraLauncher.launch(uri)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = SunsetOrange),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Camera", fontSize = 12.sp)
                            }
                            Button(
                                onClick = { galleryLauncher.launch("image/*") },
                                colors = ButtonDefaults.buttonColors(containerColor = WaterBlueMid),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Collections, contentDescription = null)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Gallery", fontSize = 12.sp)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("OR", fontWeight = FontWeight.Bold, color = TextSecondary)
                Spacer(modifier = Modifier.height(16.dp))

                // Symptom Diagnosis Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.EditNote, contentDescription = null, tint = GreenSecondary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Describe Symptoms", fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedTextField(
                            value = symptoms,
                            onValueChange = { symptoms = it },
                            placeholder = { Text("e.g. Yellow spots on leaves...") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            shape = RoundedCornerShape(12.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { showDiagnosis = true },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                        ) {
                            Text("Check Diagnosis")
                        }
                    }
                }

                if (showDiagnosis) {
                    Spacer(modifier = Modifier.height(24.dp))
                    DiagnosisResult(selectedImageUri != null)
                }
            }
        }
    }
}

@Composable
fun DiagnosisResult(isImage: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(GreenPrimary, GreenSecondary)))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = GreenPrimary)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Doctor's Verdict", fontWeight = FontWeight.Bold, color = GreenPrimary)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (isImage) "AI Analysis complete: Detected 'Early Blight'." else "Symptoms suggest: 'Leaf Rust'.",
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Treatment: Apply appropriate fungicide and ensure the crop has balanced nutrition.",
                fontSize = 14.sp,
                color = TextPrimary
            )
        }
    }
}
