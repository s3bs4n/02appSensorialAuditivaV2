package com.example.a02appsensorialauditivav2

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import android.content.SharedPreferences
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.unit.dp

@Composable
fun PantallaUsuarios(context: Context) {
    val sharedPreferences = context.getSharedPreferences("usuarios", Context.MODE_PRIVATE)
    val usuarios = obtenerUsuariosRegistrados(sharedPreferences)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Usuarios Registrados", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(usuarios.size) { index ->
                BasicText(text = usuarios[index])
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

fun obtenerUsuariosRegistrados(sharedPreferences: SharedPreferences): List<String> {
    return sharedPreferences.all.keys.toList()
}
