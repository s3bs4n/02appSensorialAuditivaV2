package com.example.a02appsensorialauditivav2

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import android.widget.Toast
import androidx.compose.foundation.shape.RoundedCornerShape
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PantallaUsuarios(navController: NavHostController) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    // Variable para almacenar la lista de usuarios
    var userList by remember { mutableStateOf(listOf<User>()) }

    // Función para obtener los usuarios de Firestore
    LaunchedEffect(Unit) {
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->
                val users = result.map { document ->
                    User(
                        id = document.id,
                        name = document.getString("name") ?: "No Name"
                    )
                }
                userList = users
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error obteniendo usuarios", Toast.LENGTH_SHORT).show()
            }
    }

    // Reestructuramos el diseño para que el botón siempre esté visible
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Bienvenido a la Pantalla de Usuarios", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Tabla de usuarios
        LazyColumn(
            modifier = Modifier
                .weight(1f) // La lista ocupará el espacio restante para que el botón esté visible
                .fillMaxWidth()
        ) {
            items(userList) { user ->
                UserRow(user)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para salir (cerrar sesión)
        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut() // Cerrar sesión en Firebase
                Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                navController.navigate("login") {
                    popUpTo("users") { inclusive = true } // Eliminar la pantalla de usuarios del backstack
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Salir", fontSize = 22.sp)
        }
    }
}

// Composable para mostrar cada usuario en una fila
@Composable
fun UserRow(user: User) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "Usuario: ${user.name}",
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        // Botón para modificar el usuario (ejemplo)
        Button(
            onClick = {
                // Aquí podrías implementar la funcionalidad para editar al usuario
            },
            modifier = Modifier.padding(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Modificar")
        }

        // Botón para eliminar el usuario
        Button(
            onClick = {
                // Aquí va la lógica para eliminar al usuario
            },
            modifier = Modifier.padding(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Eliminar")
        }
    }
}

// Clase de datos para representar un usuario
data class User(val id: String, val name: String)
