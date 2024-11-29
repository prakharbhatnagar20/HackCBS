package com.example.hackcbs.screens

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController


@Composable
fun ChatScreen(navController : NavController,
               context: Context = LocalContext.current) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val username = sharedPref.getString("username", "") ?: ""
    val url = "https://chat-n-lease-p4xb.onrender.com/message/$username"
    Column(modifier = Modifier.fillMaxSize()) {

        // WebView to load the URL
        Box(
            modifier = Modifier.weight(1f)
        ) {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        webViewClient = WebViewClient()  // Prevents redirects to external browser
                        settings.javaScriptEnabled = true // Enable JavaScript
                        loadUrl(url)
                    }
                },
                // Occupy available space above the bottom nav
            )
        }
        BottomNavBar(navController)

    }

}