package com.example.kishanmitraapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.kishanmitraapp.navigation.AppNavGraph
import com.example.kishanmitraapp.ui.theme.KishanMitraTheme
import com.example.kishanmitraapp.utils.AppLanguage
import com.example.kishanmitraapp.utils.LocalAppLanguage

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var currentLanguage by remember { mutableStateOf(AppLanguage.ENGLISH) }

            CompositionLocalProvider(LocalAppLanguage provides currentLanguage) {
                KishanMitraTheme {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        AppNavGraph(onLanguageChange = { currentLanguage = it })
                    }
                }
            }
        }
    }
}
