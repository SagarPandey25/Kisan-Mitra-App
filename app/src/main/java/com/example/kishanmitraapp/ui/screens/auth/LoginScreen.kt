package com.example.kishanmitraapp.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kishanmitraapp.data.model.LoginRequest
import com.example.kishanmitraapp.ui.theme.*
import com.example.kishanmitraapp.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val loginResult = viewModel.loginResult

    LaunchedEffect(loginResult) {
        loginResult?.let { result ->
            result.onSuccess {
                // Success is handled by the direct navigation now for better flow as requested
            }.onFailure {
                Toast.makeText(context, it.message ?: "Login failed", Toast.LENGTH_LONG).show()
                viewModel.loginResult = null
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(WheatLight, WheatGold, SoilDeep)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White.copy(alpha = 0.9f))
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome ",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = ManureDark
            )
            
            Text(
                text = "Login your's Farmer Account",
                fontSize = 14.sp,
                color = TextSecondary,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = ManureBrown) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ManureBrown,
                    focusedLabelColor = ManureBrown
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = ManureBrown) },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = ManureBrown
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ManureBrown,
                    focusedLabelColor = ManureBrown
                )
            )

            TextButton(
                onClick = { /* Forgot Password */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Forgot Password?", color = ManureBrown, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Navigate to home immediately as requested
                    onLoginSuccess()
                    
                    // Also attempt login in background to keep API connected
                    if (email.isNotBlank() && password.isNotBlank()) {
                        viewModel.login(LoginRequest(email, password))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ManureDark)
            ) {
                Text("Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Don't have an account?", fontSize = 14.sp, color = TextSecondary)
                TextButton(onClick = onRegisterClick) {
                    Text("Register", color = ManureBrown, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
