package com.example.hackcbs.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.hackcbs.data.AuthRepository

class AuthViewModel : ViewModel() {

    fun signUp(context: Context, email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        AuthRepository.signUp(context, email, password, onComplete)
    }

    fun logIn(email: String, password: String, onComplete: (Boolean, String?) -> Unit) {
        AuthRepository.logIn(email, password, onComplete)
    }
}