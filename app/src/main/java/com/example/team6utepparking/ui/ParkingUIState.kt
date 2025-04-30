package com.example.team6utepparking.ui

data class UserUIState(
    val loggedIn: Boolean = false,
    val failedLogin: Boolean = false,
    val adminLoginAttempt: Boolean = false
)
