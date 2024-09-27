package com.example.a02appsensorialauditivav2

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import com.example.a02appsensorialauditivav2.AuthViewModel

@Composable
fun PantallaRegistro(authViewModel: AuthViewModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val authState by authViewModel.authState.observeAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Imagen del logo
        Image(
            painter = painterResource(id = R.drawable.logo), // Reemplaza con tu recurso de imagen
            contentDescription = "Registro image",
            modifier = Modifier.size(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico", fontSize = 24.sp) },
            textStyle = LocalTextStyle.current.copy(fontSize = 24.sp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", fontSize = 24.sp) },
            textStyle = LocalTextStyle.current.copy(fontSize = 24.sp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de confirmación de contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirmar Contraseña", fontSize = 24.sp) },
            textStyle = LocalTextStyle.current.copy(fontSize = 24.sp),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de registro
        Button(
            onClick = {
                if (password == confirmPassword) {
                    authViewModel.register(email, password)
                } else {
                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00BFFF)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Registrarse", fontSize = 24.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje de estado del registro
        authState?.let { state ->
            if (state.isSuccess) {
                Toast.makeText(context, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show()
            } else if (state.isError) {
                Toast.makeText(context, "Error: ${state.errorMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
