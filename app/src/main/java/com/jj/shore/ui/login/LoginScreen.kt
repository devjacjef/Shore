@file:OptIn(ExperimentalMaterial3Api::class)

package com.jj.shore.ui.login

import android.widget.Space
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.PasswordCredential
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.jj.shore.ui.AppViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val isLoading = remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Box(
        Modifier
            .padding(16.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoginForm(
            email = email,
            password = password,
            onEmailChange = { email = it },
            onPasswordChange = { password = it },
            onSignInClick = {
                isLoading.value = true
                viewModel.signIn(email, password)
            }
        )
    }

}

@Composable
fun LoginForm(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Text("Shore", fontSize = 64.sp)

        Spacer(Modifier.size(16.dp))

        TextField(
            value = email,
            onValueChange = onEmailChange,
            label = { Text("Email") },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(
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
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.size(32.dp))

        Button(onClick = onSignInClick,
            Modifier.height(48.dp).width(200.dp)) {
            Text("Sign In")
        }
    }
}
