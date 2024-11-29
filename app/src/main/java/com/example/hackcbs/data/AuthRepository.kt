package com.example.hackcbs.data


import android.content.Context
import android.util.Log
import com.example.hackcbs.network.ApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import java.util.UUID

object AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun signUp(context: Context,email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val username = generateUniqueUsername(email)
                        Log.e("Username", "$username")

                        checkAndSaveUsername(uid, username, context = context) { success, errorMessage ->
                            if (success) {
                                // Generate FCM token after successful username setup
                                FirebaseMessaging.getInstance().token
                                    .addOnCompleteListener { tokenTask ->
                                        if (tokenTask.isSuccessful) {
                                            val fcmToken = tokenTask.result
                                            Log.e("fcmToken1", "$fcmToken")
                                            onComplete(true, "Failed to generate FCM token")
//                                            val userData = UserData(username, fcmToken ?: "")
//                                            ApiClient.apiService.sendUserData(userData).enqueue(object : retrofit2.Callback<ApiResponse> {
//                                                override fun onResponse(call: Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
//                                                    if (response.isSuccessful && response.body()?.success == true) {
//                                                        onComplete(true, null)
//                                                    } else {
//                                                        onComplete(false, response.body()?.message ?: "Unknown error")
//                                                    }
//                                                }
//
//                                                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                                                    onComplete(false, t.message)
//                                                }
//                                            })
                                        } else {
                                            Log.e("SignUp Error1", "Error: ${task.exception?.message}")
                                            onComplete(false, "Failed to generate FCM token")
                                        }
                                    }
                            } else {
                                Log.e("SignUp Error1", "Error: ${task.exception?.message}")
                                onComplete(false, errorMessage)
                            }
                        }
                    } else {
                        Log.e("SignUp Error2", "Error: ${task.exception?.message}")
                        onComplete(false, "User ID not available")
                    }
                } else {
                    Log.e("SignUp Error", "Error: ${task.exception?.message}")
                    onComplete(false, task.exception?.message)
                }
            }
    }
    private fun saveUsernameInPreferences(context: Context, username: String) {
        val sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().putString("username", username).apply()
    }

    private fun generateUniqueUsername(email: String): String {
        // Extracts a part of the email and adds a random suffix to it
        val prefix = email.substringBefore("@")
        val suffix = UUID.randomUUID().toString().take(5) // 5-character unique suffix
        return "$prefix$suffix"
    }

    private fun checkAndSaveUsername(uid: String, username: String,  context: Context,onComplete: (Boolean, String?) -> Unit) {
        val usernamesRef = db.collection("usernames")

        // Check if username already exists
        Log.e("UsernameRef", "${usernamesRef}")
        usernamesRef.document(username).get().addOnSuccessListener { document ->
            if (!document.exists()) {
                // Username is unique; save it in the database
                usernamesRef.document(username).set(mapOf("uid" to uid))
                    .addOnSuccessListener {
                        db.collection("users").document(uid).set(mapOf("username" to username))
                        saveUsernameInPreferences(context, username)
                        onComplete(true, null)
                    }
                    .addOnFailureListener { exception ->
                        onComplete(false, exception.message)
                    }
            } else {
                // Retry with a new username if collision occurs
                val newUsername = generateUniqueUsername(username)
                checkAndSaveUsername(uid, newUsername,context ,onComplete)
            }
        }.addOnFailureListener { exception ->
            onComplete(false, exception.message)
        }
    }

    fun logIn(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    onComplete(false, task.exception?.message)
                }
            }
    }
}