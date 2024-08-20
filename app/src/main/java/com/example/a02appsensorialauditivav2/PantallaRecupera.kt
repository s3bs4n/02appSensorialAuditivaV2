package com.example.a02appsensorialauditivav2


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.EmailVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavHostController

@Composable
fun RecuperarContrasenaScreen(navController: NavHostController, context: Context) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = "Recuperar Contraseña image",
            modifier = Modifier.size(300.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Correo Electrónico") },
//            visualTransformation = EmailVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                val sharedPreferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE)
                val storedPassword = sharedPreferences.getString(email, null)

                if (storedPassword != null) {
                    password = storedPassword
                    Toast.makeText(context, "La contraseña es: $password", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Usuario incorrecto o inexistente", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(310.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF00BFFF)
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Recordar")
        }

        Spacer(modifier = Modifier.height(16.dp))

//        FUNCION PARA IR A LOGIN
        val annotatedText = buildAnnotatedString {
            append("¿Ya recordaste tu contraseña? ")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append("Inicia Sesión")
            }
        }

        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                navController.navigate("login")
            }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}
