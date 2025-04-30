package com.example.team6utepparking

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.team6utepparking.ui.LoginScreen
import com.example.team6utepparking.ui.PermitScreen
import com.example.team6utepparking.ui.UserViewModel
import com.example.team6utepparking.ui.theme.Team6UTEPParkingTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

enum class ParkingScreen() {
    Login,
    Permit
}

@Composable
fun ParkingApp(
    startingPage: String,
    navController: NavHostController = rememberNavController(),
    userViewModel: UserViewModel = viewModel()
) {
    Scaffold(
        containerColor = Color(0, 0, 128),

        //modifier = Modifier.fillMaxSize()

    ) { innerPadding ->
        val coroutineScope = rememberCoroutineScope()

        NavHost(
            navController = navController,
            startDestination = startingPage,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ){

            composable(route = ParkingScreen.Login.name) {
                LoginScreen(
                    onSubmitButtonClicked = { email:String, password:String ->
                        userViewModel.login(email, password)
                            .onEach { response ->
                                if (response is AuthResponse.Success) {
                                    navController.navigate(ParkingScreen.Permit.name)
                                }
                            }
                            .launchIn(coroutineScope)
                    },
                    userViewModel = userViewModel,

                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            composable(route = ParkingScreen.Permit.name) {
                PermitScreen(
                    onLogoutButtonClicked = { navController.navigate(ParkingScreen.Login.name) },
                    userViewModel = userViewModel
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ParkingPreview() {
    Team6UTEPParkingTheme {
        ParkingApp(ParkingScreen.Permit.name)
    }
}