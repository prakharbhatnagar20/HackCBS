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
fun MapScreen(navController : NavController,
              context: Context = LocalContext.current) {
    val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val username = sharedPref.getString("username", "") ?: ""

    val url = "https://cd57-2401-4900-5d8a-eb1f-55c4-5e34-324c-530b.ngrok-free.app/map?username=$username"
//    val url = "https://www.google.com/"

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