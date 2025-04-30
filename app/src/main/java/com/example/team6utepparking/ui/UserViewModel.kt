package com.example.team6utepparking.ui

import androidx.lifecycle.ViewModel
import com.example.team6utepparking.AuthResponse
import com.example.team6utepparking.domain.model.User
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow

class UserViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val _uiState = MutableStateFlow(UserUIState())
    private var _user = MutableStateFlow<User?>(null)

    val uiState: StateFlow<UserUIState> = _uiState.asStateFlow()
    var user = _user.asStateFlow()


    init {
        if(auth.currentUser != null){
            getUser()
        }
    }

    private fun getUser(){

        val userId = auth.currentUser?.uid

        if(userId != null){
            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if(documentSnapshot.exists()){
                        _user.value = documentSnapshot.toObject()
                    }
                }
        }
    }

    fun updateUser(){

        val userId = auth.currentUser?.uid

        if(userId != null){
            db.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if(documentSnapshot.exists()){
                        _user.value = documentSnapshot.toObject()
                    }
                }
        }
    }

    fun login(email: String, password: String): Flow<AuthResponse> = callbackFlow {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    trySend(AuthResponse.Success)
                    _uiState.value = UserUIState(loggedIn = true)
                } else {
                    trySend(AuthResponse.Error(message = task.exception?.message ?: ""))
                    _uiState.value = UserUIState(failedLogin = true)
                }
            }
        awaitClose()
    }

    fun confirmFailedLogIn(){
        _uiState.value = UserUIState(failedLogin = false)
    }

    fun logout(){
        _uiState.value = UserUIState(loggedIn = false)
        auth.signOut()
    }
}