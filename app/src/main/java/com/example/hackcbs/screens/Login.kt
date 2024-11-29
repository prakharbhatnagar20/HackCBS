package com.example.hackcbs.screens


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.hackcbs.viewmodels.AuthViewModel

@Composable
fun LoginPage(viewModel: AuthViewModel = viewModel(), navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isSignUp by remember { mutableStateOf(false) }  // Toggle between Login and Sign-Up
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = if (isSignUp) "Create an Account" else "Welcome Back",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Email Field with Icon
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = MaterialTheme.colors.primary,
//                unfocusedBorderColor = Color.Gray
//            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Field with Icon
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Password Icon")
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                focusedBorderColor = MaterialTheme.colors.primary,
//                unfocusedBorderColor = Color.Gray
//            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sign In/Sign Up Button
        Button(
            onClick = {
                if (isSignUp) {
                    viewModel.signUp(context,email, password) { success, error ->
                        Log.e("SignUp", "${success}")
                        if (success) {
                            navController.navigate("resents")
                        } else {
                            // Handle error
                        }
                    }
                } else {
                    viewModel.logIn(email, password) { success, error ->
                        if (success) {
                            navController.navigate("resents")
                        } else {
                            // Handle error
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp)),
//            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = if (isSignUp) "Sign Up" else "Log In",
//                style = MaterialTheme.typography.button.copy(color = Color.White)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Switch between Login/Sign Up
        TextButton(onClick = { isSignUp = !isSignUp }) {
            Text(
                text = if (isSignUp) "Already have an account? Log In" else "Don't have an account? Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

