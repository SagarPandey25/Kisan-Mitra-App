package com.example.kishanmitraapp.ui.components

import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureCard(title: String, onClick: () -> Unit) {
    Card(onClick = onClick) {
        Text(text = title)
    }
}
