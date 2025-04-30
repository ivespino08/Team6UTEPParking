package com.example.team6utepparking.ui

import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.team6utepparking.ui.theme.Team6UTEPParkingTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun PermitScreen (
    onLogoutButtonClicked: () -> Unit,
    userViewModel: UserViewModel,
    parkingViewModel: ParkingViewModel = viewModel(),
    carListViewModel: CarListViewModel = viewModel()
){


    val parkingPermit by parkingViewModel.parkingPermit.collectAsStateWithLifecycle()
    parkingViewModel.getLot(parkingPermit?.permitType)

    val parkingLot by parkingViewModel.parkinglot.collectAsStateWithLifecycle()

    userViewModel.updateUser()
    val user by userViewModel.user.collectAsStateWithLifecycle()

    carListViewModel.getCars(Firebase.auth.currentUser?.uid)
    val cars by carListViewModel.carList.collectAsStateWithLifecycle()



    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Surface (
            color = Color(255, 128, 0),
            modifier = Modifier.fillMaxWidth()
        ) {
             Row (
                 horizontalArrangement = Arrangement.End
            ) {
                 Button(
                     onClick = {
                         userViewModel.logout()
                         onLogoutButtonClicked()
                     },
                 ) {
                     Text( text = "Logout")
                 }
            }

        }

        Spacer(modifier = Modifier.padding(120.dp))

        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Card(
                modifier = Modifier.padding(15.dp)

            ) {

                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        text = "${parkingLot?.name}",
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )


                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Availability: ${parkingLot?.capacity?.minus(parkingLot?.occupied!!)}/${parkingLot?.capacity}",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Valid to December 11, 2025",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            var listHeight = min(((60*cars.size)+60).dp, 240.dp)

            Card (
                modifier = Modifier.padding(20.dp)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(20.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.height(listHeight)
                ) {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 10.dp)
                        ) {
                            Text(text = "Make", fontWeight = FontWeight.Bold)
                            Text(text = "Model", fontWeight = FontWeight.Bold)
                            Text(text = "License Plate", fontWeight = FontWeight.Bold)
                        }
                    }


                    items(cars) { car ->

                     Row(
                         horizontalArrangement = Arrangement.SpaceBetween,
                         modifier = Modifier
                             .fillMaxWidth()
                             .padding(vertical = 10.dp)
                     ) {
                         Text(text = car.make, fontWeight = FontWeight.Bold)
                         Text(text = car.model, fontWeight = FontWeight.Bold)
                         Text(text = car.licensePlate, fontWeight = FontWeight.Bold)
                     }

                    }
                }

            }
                
 



            if(user?.parked == false){
                Button(
                    onClick = {
                        parkingViewModel.park(parkingPermit?.permitType)
                        userViewModel.updateUser()
                    },
                    colors = ButtonDefaults.buttonColors(Color(0,128,0))
                ) {
                    Text( text = "Park")
                }
            } else if (user?.parked == true){
                Button(
                    onClick = {
                        parkingViewModel.unpark(parkingPermit?.permitType)
                        userViewModel.updateUser()
                    },
                    colors = ButtonDefaults.buttonColors(Color(128,0,0))
                ) {
                    Text( text = "Unpark")
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PermitPreview() {
    Team6UTEPParkingTheme {
        //PermitScreen(onLogoutButtonClicked = {ParkingScreen.Login.name})
    }
}