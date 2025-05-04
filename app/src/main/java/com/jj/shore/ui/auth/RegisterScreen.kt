package com.jj.shore.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jj.shore.ui.AppViewModelProvider

/**
 * REFERENCES
 *
 */

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RegisterForm(email,
            password,
            repeatPassword,
            errorMessage = errorMessage,
            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onRepeatPasswordChange = { repeatPassword = it },
            onRegisterClick = {
                viewModel.register(
                    email,
                    password,
                    repeatPassword,
                    errorMessage = { message -> errorMessage = message })
            })
    }

}

@Composable
fun RegisterForm(
    email: String,
    password: String,
    repeatPassword: String,
    errorMessage: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text("Shore", fontSize = 64.sp)

        Spacer(Modifier.size(16.dp))

        TextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            )
        )

        Spacer(Modifier.size(16.dp))

        TextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Password") },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.size(16.dp))

        TextField(
            value = repeatPassword,
            onValueChange = onRepeatPasswordChange,
            label = { Text("Confirm Password") },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(Modifier.size(8.dp))
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp)
            )
        }


        Spacer(Modifier.size(32.dp))

        Button(
            onClick = onRegisterClick,
            Modifier
                .height(48.dp)
                .width(200.dp)
        ) {
            Text("Sign Up")
        }
    }
}