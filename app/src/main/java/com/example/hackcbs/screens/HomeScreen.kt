package com.example.hackcbs.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController


@Composable
fun HomeScreen(url: String = "https://www.google.com/",navController : NavController) {


    // Column to hold the WebView and Bottom Navigation Bar
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

@Composable
fun BottomNavBar(navController : NavController) {
    var selectedItem: Int by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(66.dp)
            .background(Color.LightGray)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Home Button
        IconButton(
            onClick = {  navController.navigate("resents")},
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Home",
            )
        }

        // Favorites Button
        IconButton(
            onClick = { selectedItem = 1
                        navController.navigate("map")
                      },
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.Place,
                contentDescription = "Maps",

                )
        }

        // Settings Button
        IconButton(
            onClick = { selectedItem = 2
                navController.navigate("chat")},
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = "Chat",

                )
        }
        IconButton(
            onClick = { selectedItem = 3
                navController.navigate("lease")},
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Lease",

                )
        }
    }



}
