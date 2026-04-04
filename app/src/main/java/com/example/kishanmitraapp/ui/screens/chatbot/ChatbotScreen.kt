package com.example.kishanmitraapp.ui.screens.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.kishanmitraapp.ui.theme.*
import kotlinx.coroutines.launch

data class Message(val text: String, val isUser: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(onBackClick: () -> Unit) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf(
        Message("Hello! I am your Kisan Sahayak. How can I help you today?", false)
    ) }
    
    var selectedLanguage by remember { mutableStateOf("English") }
    val languages = listOf("English", "Hindi", "Punjabi", "Marathi", "Gujarati")
    var showLanguageMenu by remember { mutableStateOf(false) }
    
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.SmartToy, contentDescription = null, tint = GreenPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Kisan Sahayak AI", fontWeight = FontWeight.Bold)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { showLanguageMenu = true }) {
                            Icon(Icons.Default.Language, contentDescription = "Language")
                        }
                        DropdownMenu(
                            expanded = showLanguageMenu,
                            onDismissRequest = { showLanguageMenu = false }
                        ) {
                            languages.forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text(lang) },
                                    onClick = {
                                        selectedLanguage = lang
                                        showLanguageMenu = false
                                        messages.add(Message("Language changed to $lang", false))
                                    }
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Background Farmer Image
            AsyncImage(
                model = "https://images.unsplash.com/photo-1592982537447-7440770cbfc9?q=80&w=1000&auto=format&fit=crop",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.2f
            )
            
            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.7f),
                                BackgroundLight.copy(alpha = 0.9f)
                            )
                        )
                    )
            )

            Column(modifier = Modifier.fillMaxSize()) {
                // Messages Area
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(messages) { msg ->
                        ChatBubble(msg)
                    }
                }

                // Input Area
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shadowElevation = 16.dp,
                    color = Color.White.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .navigationBarsPadding()
                            .imePadding(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextField(
                            value = messageText,
                            onValueChange = { messageText = it },
                            placeholder = { Text("Ask anything about farming...") },
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(24.dp)),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = BackgroundLight.copy(alpha = 0.5f),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            maxLines = 3
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        FloatingActionButton(
                            onClick = {
                                if (messageText.isNotBlank()) {
                                    val userMsg = messageText
                                    messages.add(Message(userMsg, true))
                                    messageText = ""
                                    // Simulate AI response
                                    messages.add(Message("Processing your request about '$userMsg'...", false))
                                }
                            },
                            containerColor = GreenPrimary,
                            contentColor = Color.White,
                            shape = CircleShape,
                            modifier = Modifier.size(52.dp)
                        ) {
                            Icon(Icons.Default.Send, contentDescription = "Send")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    val arrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    val bubbleColor = if (message.isUser) GreenPrimary else Color.White.copy(alpha = 0.9f)
    val textColor = if (message.isUser) Color.White else TextPrimary
    val shape = if (message.isUser) {
        RoundedCornerShape(16.dp, 16.dp, 4.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = arrangement
    ) {
        if (!message.isUser) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(GreenPrimary.copy(alpha = 0.1f))
                    .align(Alignment.Bottom),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.SmartToy,
                    contentDescription = null,
                    tint = GreenPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        
        Card(
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = bubbleColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                color = textColor,
                fontSize = 15.sp,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}
