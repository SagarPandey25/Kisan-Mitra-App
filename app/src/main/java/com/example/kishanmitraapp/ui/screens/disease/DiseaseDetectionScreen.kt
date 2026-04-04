package com.example.kishanmitraapp.ui.screens.disease

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.kishanmitraapp.ui.theme.*
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiseaseDetectionScreen(onBackClick: () -> Unit) {
    val context = LocalContext.current
    var symptoms by remember { mutableStateOf("") }
    var showDiagnosis by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }
    var isUploading by remember { mutableStateOf(false) }

    // Permission Launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val uri = getTempUri(context)
            tempImageUri = uri
        }
    }

    // Gallery Launcher
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        if (uri != null) showDiagnosis = false // Hide diagnosis until upload
    }

    // Camera Launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            selectedImageUri = tempImageUri
            showDiagnosis = false // Hide diagnosis until upload
        }
    }

    fun launchCamera() {
        val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            val uri = getTempUri(context)
            tempImageUri = uri
            cameraLauncher.launch(uri)
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
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
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column {
                            Box(modifier = Modifier.fillMaxWidth().height(250.dp)) {
                                AsyncImage(
                                    model = selectedImageUri,
                                    contentDescription = "Selected Image",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                                // Remove actions
                                IconButton(
                                    onClick = { 
                                        selectedImageUri = null
                                        showDiagnosis = false
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Remove", tint = Color.White)
                                }
                            }
                            
                            // Upload and Recapture Section
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { launchCamera() },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = SunsetOrange)
                                ) {
                                    Icon(Icons.Default.Refresh, contentDescription = null)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Recapture")
                                }
                                
                                Button(
                                    onClick = { 
                                        isUploading = true
                                        // Simulate upload
                                        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                            isUploading = false
                                            showDiagnosis = true
                                            Toast.makeText(context, "Image uploaded successfully!", Toast.LENGTH_SHORT).show()
                                        }, 2000)
                                    },
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                                    enabled = !isUploading
                                ) {
                                    if (isUploading) {
                                        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White, strokeWidth = 2.dp)
                                    } else {
                                        Icon(Icons.Default.CloudUpload, contentDescription = null)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Upload")
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Initial Selection Card (only show if no image selected)
                if (selectedImageUri == null) {
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
                                    onClick = { launchCamera() },
                                    colors = ButtonDefaults.buttonColors(containerColor = SunsetOrange),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Camera")
                                }
                                Button(
                                    onClick = { galleryLauncher.launch("image/*") },
                                    colors = ButtonDefaults.buttonColors(containerColor = WaterBlueMid),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(Icons.Default.Collections, contentDescription = null)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Gallery")
                                }
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

private fun getTempUri(context: android.content.Context): Uri {
    val tempFile = File.createTempFile("temp_image", ".jpg", context.externalCacheDir).apply {
        createNewFile()
        deleteOnExit()
    }
    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", tempFile)
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
