package com.example.hackcbs.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController


@Composable
fun LeaseScreen(url: String = "https://www.youtube.com/", navController : NavController) {

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