package com.example.team6utepparking.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.team6utepparking.R
import com.example.team6utepparking.ui.theme.Team6UTEPParkingTheme


@Composable
fun LoginScreen(
    modifier: Modifier,
    onSubmitButtonClicked: (String, String) -> Unit,
    userViewModel: UserViewModel
){


    val userUIState by userViewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }



    Column {
        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()

        ) {
            Image(
                painter = painterResource(R.drawable.uteplogo),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
        }


        Column(
            modifier = Modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
        ) {

            Text(
                text = "Sign In",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Please sign in with your email and password",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(40.dp))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { newValue ->
                        email = newValue
                    },
                    placeholder = {
                        Text(text = "Email")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Email,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            ) {
                OutlinedTextField(
                    value = password,
                    onValueChange = { newValue ->
                        password = newValue
                    },
                    placeholder = {
                        Text(text = "Password")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            contentDescription = null
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    if (email != "" && password != "") {
                        onSubmitButtonClicked(email, password)
                    }
                },

                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Sign In",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 3.dp)
                )
            }
        }

        if(userUIState.failedLogin){
            FailedLogin({userViewModel.confirmFailedLogIn()})
        }
    }
}

@Composable
fun FailedLogin(
    confirm: () -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
        },
        title = { Text(text = "Invalid Credentials") },
        text = { Text(text = "Incorrect email and/or password") },
        modifier = modifier,
        confirmButton = {
            TextButton(
                onClick = { confirm() }
            ) {
                Text(text = "Confirm")
            }
        }
    )

}

@Preview(showBackground = true)
@Composable
private fun LoginPreview() {
    Team6UTEPParkingTheme {
        //LoginScreen()
    }
}