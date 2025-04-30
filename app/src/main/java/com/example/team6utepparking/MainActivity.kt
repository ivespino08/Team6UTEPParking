package com.example.team6utepparking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.team6utepparking.ui.theme.Team6UTEPParkingTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.FirebaseAuth


private lateinit var auth: FirebaseAuth

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

    }

    override fun onStart() {
        super.onStart()

        setContent {
            Team6UTEPParkingTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val startingPage: String = if (auth.currentUser == null){
                        ParkingScreen.Login.name
                    } else {
                        ParkingScreen.Permit.name
                    }

                    ParkingApp(startingPage)
                }
            }
        }
    }
}

interface AuthResponse {
    data object Success: AuthResponse
    data class Error(val message: String): AuthResponse
}
