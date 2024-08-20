package com.example.a02appsensorialauditivav2

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavHostController
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp


@Composable
fun RegistroScreen(navController: NavHostController, context: Context) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    fun saveUser(context: Context, email: String, password: String) {
        val sharedPreferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(email, password)
        editor.apply()
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = "Registro image",
            modifier = Modifier.size(300.dp)
        )

        OutlinedTextField(value = email, onValueChange = {
            email = it
        }, label = {
            Text(text = "Correo Electrónico")
        })

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = password, onValueChange = {
            password = it
        }, label = {
            Text(text = "Contraseña")
        }, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(value = confirmPassword, onValueChange = {
            confirmPassword = it
        }, label = {
            Text(text = "Confirmar Contraseña")
        }, visualTransformation = PasswordVisualTransformation())

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (password == confirmPassword) {
                    Log.i("Registro", "Email : $email, Password : $password")
                    // Guardar el usuario en SharedPreferences
                    saveUser(context, email, password)
                    Toast.makeText(context, "Registro Exitoso", Toast.LENGTH_SHORT).show()

                } else {
                    Log.e("Registro", "Las contraseñas no coinciden")
                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
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
            Text(text = "Registrarse")
        }

        Spacer(modifier = Modifier.height(16.dp))

//        FUNCION PARA IR A LOGIN
        val annotatedText = buildAnnotatedString {
            append("¿Ya tienes una cuenta? ")
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
