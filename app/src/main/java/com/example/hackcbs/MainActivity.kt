package com.example.hackcbs

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.Manifest
import android.app.Activity
import android.location.Location
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hackcbs.screens.ChatScreen
import com.example.hackcbs.screens.HomeScreen
import com.example.hackcbs.screens.LeaseScreen
import com.example.hackcbs.screens.LoginPage
import com.example.hackcbs.screens.MapScreen
import com.example.hackcbs.ui.theme.HackCBSTheme
import com.example.hackcbs.viewmodels.LocationViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val viewModel: LocationViewModel = viewModel()
            HackCBSTheme {
                MyApp(viewModel)
            }
        }
    }


}

@Composable
fun MyApp(viewModel: LocationViewModel) {
    val context = LocalContext.current
    val locationUtils = LocationUtils(context)
    val activity = context as? Activity
    activity?.let {
        LocationDisplay(locationUtils = locationUtils, viewModel = viewModel, context  = context,activity = activity)
    }
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "chat") {
        composable("login") {
            LoginPage(navController = navController)
        }
        composable("resents") {
            HomeScreen(navController = navController)
        }
        composable("map") {
            MapScreen(navController = navController)
        }

        composable("chat") {
            ChatScreen(navController = navController)
        }
        composable("lease") {
            LeaseScreen(navController = navController)
        }


    }

}
@Composable
fun LocationDisplay(
    locationUtils: LocationUtils,
    viewModel: LocationViewModel,
    context: Context,
    activity: Activity
) {
    val location = viewModel.location.value

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
            ) {
                // I HAVE ACCESS to location
                locationUtils.requestLocationUpdates(viewModel = viewModel)
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )

                if (rationaleRequired) {
                    Toast.makeText(
                        context,
                        "Location Permission is required for this feature to work",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Location Permission is required. Please enable it in the Android Settings",
                        Toast.LENGTH_LONG
                    ).show()
                    activity.finish()
                }
            }
        }
    )

    LaunchedEffect(location) {
        if (location == null) {
            if (locationUtils.hasLocationPermission(context)) {
                // Permission already granted update the location
                locationUtils.requestLocationUpdates(viewModel)
            } else {
                // Request location permission
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        } else {
            // Send location data when location is updated
            val locationAndroid = Location("").apply {
                latitude = location.latitude
                longitude = location.longitude
            }
            viewModel.sendLocationData(locationAndroid)
        }
    }

//    DisposableEffect(Unit) {
//        onDispose {
//            locationUtils.stopLocationUpdates(viewModel)
//        }
//    }


}



