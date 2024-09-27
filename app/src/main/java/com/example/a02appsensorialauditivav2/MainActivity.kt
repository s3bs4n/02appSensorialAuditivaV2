package com.example.a02appsensorialauditivav2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a02appsensorialauditivav2.ui.theme._02appSensorialAuditivaV2Theme
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Importación necesaria para Firebase
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "login") {
                composable("login") { PantallaLogin(navController) }
                composable("register") { PantallaRegistro() }
                composable("recover") { RecuperarContrasenaScreen(navController, context) }
                composable("users") { PantallaUsuarios(context) }
            }
        }
    }
}
