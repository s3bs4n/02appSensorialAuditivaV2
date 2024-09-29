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
    var userList by remember { mutableStateOf(listOf<Usuarios>()) }

    // Función para obtener los usuarios de Firestore
    LaunchedEffect(Unit) {
        db.collection("usuarios")
            .get()
            .addOnSuccessListener { result ->
                val users = result.map { document ->
                    Usuarios(
                        email = document.getString("email") ?: "",
                        nombre = document.getString("nombre") ?: "",
                        apellidoPaterno = document.getString("apellidoPaterno") ?: "",
                        apellidoMaterno = document.getString("apellidoMaterno") ?: "",
                        telefono = document.getString("telefono") ?: "",
                        direccion = document.getString("direccion") ?: ""
                    )
                }
                userList = users
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error obteniendo usuarios", Toast.LENGTH_SHORT).show()
            }
    }

    // Función para eliminar usuario
    fun eliminarUsuario(email: String) {
        db.collection("usuarios").document(email)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                userList = userList.filter { it.email != email }  // Remover de la lista
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
            }
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text(text = "Bienvenido a la Pantalla de Usuarios", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Tabla de usuarios
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(userList) { user ->
                UserRow(user, ::eliminarUsuario)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
                navController.navigate("login") {
                    popUpTo("users") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth().height(60.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Salir", fontSize = 22.sp)
        }
    }
}

@Composable
fun UserRow(user: Usuarios, eliminarUsuario: (String) -> Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Text(
            text = "Usuario: ${user.email} - ${user.nombre} ${user.apellidoPaterno}",
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        Button(
            onClick = { eliminarUsuario(user.email) },
            modifier = Modifier.padding(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Eliminar")
        }
    }
}
