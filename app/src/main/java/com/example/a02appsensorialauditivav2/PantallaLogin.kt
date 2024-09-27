package com.example.a02appsensorialauditivav2

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PantallaLogin(navController: NavHostController, authViewModel: AuthViewModel = viewModel()) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val authState by authViewModel.authState.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Correo Electrónico") },
            textStyle = TextStyle(fontSize = 20.sp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Contraseña") },
            textStyle = TextStyle(fontSize = 20.sp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de iniciar sesión
        Button(
            onClick = {
                authViewModel.login(email, password) // Intentar iniciar sesión en Firebase
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Iniciar Sesión", fontSize = 22.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Mensajes de éxito o error al iniciar sesión
        authState?.let { state ->
            if (state.isSuccess) {
                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
                // Navegar a otra pantalla (ejemplo: PantallaUsuarios) después del login
                navController.navigate("users") // Asegúrate de que la ruta esté definida en tu NavHost
            } else if (state.isError) {
                Toast.makeText(context, "Error: ${state.errorMessage}", Toast.LENGTH_LONG).show()
            }
        }

        // Enlace a la pantalla de registro si no tienes una cuenta
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate("register") }) {
            Text(text = "¿No tienes cuenta? Regístrate", fontSize = 18.sp, color = Color.Blue)
        }
    }
}
