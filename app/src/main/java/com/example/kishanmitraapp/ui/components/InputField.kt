package com.example.kishanmitraapp.ui.components
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
@Composable fun InputField(value:String,onValueChange:(String)->Unit){
    TextField(value = value, onValueChange = onValueChange)
}
