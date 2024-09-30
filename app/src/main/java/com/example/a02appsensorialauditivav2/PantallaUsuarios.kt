package com.example.a02appsensorialauditivav2

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PantallaUsuarios(navController: NavHostController) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser

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

    // Función para modificar el correo del usuario
    fun modificarCorreoUsuario(id: String, nuevoCorreo: String) {
        // Buscar el documento por el campo "email" en lugar del ID del documento
        db.collection("usuarios").whereEqualTo("email", id)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents[0]  // Obtener el primer documento encontrado
                    val documentId = document.id  // ID del documento real en Firestore

                    // Actualizar el campo "email" en el documento encontrado
                    db.collection("usuarios").document(documentId)
                        .update("email", nuevoCorreo)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Correo actualizado en Firestore", Toast.LENGTH_SHORT).show()

                            // Actualizar en Firebase Authentication
                            val user = FirebaseAuth.getInstance().currentUser
                            user?.let {
                                it.verifyBeforeUpdateEmail(nuevoCorreo)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            Toast.makeText(context, "Correo de verificación enviado. Revisa tu bandeja de entrada.", Toast.LENGTH_SHORT).show()
                                            // Actualizar la lista local de usuarios
                                            userList = userList.map { if (it.email == id) it.copy(email = nuevoCorreo) else it }
                                        } else {
                                            Toast.makeText(context, "Error al enviar verificación: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                            } ?: run {
                                Toast.makeText(context, "Usuario no encontrado o no autenticado", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Error al actualizar correo en Firestore", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(context, "No se encontró un usuario con este correo", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al buscar documento: ${e.message}", Toast.LENGTH_LONG).show()
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
                UserRow(user, ::eliminarUsuario, ::modificarCorreoUsuario)
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

// Aquí agregamos el dialog para modificar el correo
@Composable
fun UserRow(user: Usuarios, eliminarUsuario: (String) -> Unit, modificarCorreoUsuario: (String, String) -> Unit) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var nuevoCorreo by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    ) {
        Text(
            text = "Usuario: ${user.email} - ${user.nombre} ${user.apellidoPaterno}",
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )

        // Botón para modificar el correo
        Button(
            onClick = { showDialog = true },  // Muestra el diálogo
            modifier = Modifier.padding(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text("Modificar")
        }

        // Botón para eliminar el usuario
        Button(
            onClick = { eliminarUsuario(user.email) },
            modifier = Modifier.padding(horizontal = 4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Eliminar")
        }

        // Dialog para ingresar el nuevo correo
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Modificar Correo") },
                text = {
                    Column {
                        Text("Nuevo correo:")
                        TextField(value = nuevoCorreo, onValueChange = { nuevoCorreo = it })
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            modificarCorreoUsuario(user.email, nuevoCorreo)
                            showDialog = false
                        }
                    ) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
