package com.example.a02appsensorialauditivav2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.a02appsensorialauditivav2.ui.theme._02appSensorialAuditivaV2Theme
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "login") {
                composable("login") { LoginScreen(navController, context) }
                composable("register") { RegistroScreen(navController, context) }
                composable("recover") { RecuperarContrasenaScreen(navController, context) }
                composable("users") { PantallaUsuarios(context) }

            }
        }
//        setContent {
//            LoginScreen()
//        }
//        setContent {
//            RegistroScreen()
//        }

    }
}