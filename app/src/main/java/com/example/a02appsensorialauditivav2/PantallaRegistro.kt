package com.example.a02appsensorialauditivav2

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp // Import para dp
import androidx.compose.runtime.livedata.observeAsState // Import para observeAsState
import com.example.a02appsensorialauditivav2.AuthViewModel // Asegúrate de que esté correcto

@Composable
fun PantallaRegistro(authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val authState by authViewModel.authState.observeAsState() // Observa el estado
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Corregido el uso de dp
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(modifier = Modifier.height(16.dp)) // Corregido el uso de dp
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp)) // Corregido el uso de dp
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp)) // Corregido el uso de dp
        Button(
            onClick = {
                if (password == confirmPassword) {
                    authViewModel.register(email, password)
                } else {
                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text("Registrar")
        }

        authState?.let { state -> // Corregido el uso de 'it'
            if (state.isSuccess) {
                Toast.makeText(context, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show()
            } else if (state.isError) {
                Toast.makeText(context, "Error: ${state.errorMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
