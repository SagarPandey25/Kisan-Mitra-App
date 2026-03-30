package com.example.kishanmitraapp.ui.components
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
@Composable fun CustomButton(text:String,onClick:()->Unit){
    Button(onClick = onClick){ Text(text) }
}
