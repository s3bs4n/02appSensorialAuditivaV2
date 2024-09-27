package com.example.a02appsensorialauditivav2

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = "Login image",
            modifier = Modifier.size(300.dp)
        )
        // Campo de correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico", fontSize = 24.sp) },
            textStyle = TextStyle(fontSize = 24.sp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", fontSize = 24.sp) },
            textStyle = TextStyle(fontSize = 24.sp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = { navController.navigate("recover") }) {
            Text(text = "¿Olvidaste tu Contraseña?", fontSize = 18.sp, color = Color.DarkGray)
        }
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

        Spacer(modifier = Modifier.height(16.dp))



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

        Text(text = "O inicia sesión con", fontSize = 18.sp, color = Color.DarkGray)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(40.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(painter = painterResource(id = R.drawable.fb),
                contentDescription = "Facebook",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { /* Facebook clicked */ }
            )

            Image(painter = painterResource(id = R.drawable.google),
                contentDescription = "Google",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { /* Google clicked */ }
            )

            Image(painter = painterResource(id = R.drawable.twitter),
                contentDescription = "Twitter",
                modifier = Modifier
                    .size(60.dp)
                    .clickable { /* Twitter clicked */ }
            )
        }


//        Spacer(modifier = Modifier.height(16.dp))


        // Enlace a la pantalla de registro si no tienes una cuenta
        TextButton(onClick = { navController.navigate("register") }) {
            Text(text = "¿No tienes cuenta? Regístrate", fontSize = 18.sp, color = Color.DarkGray)
        }

        Spacer(modifier = Modifier.weight(1f))


    }
}
