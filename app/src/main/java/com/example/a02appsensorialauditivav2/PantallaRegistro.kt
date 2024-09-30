import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import com.example.a02appsensorialauditivav2.R

@Composable
fun PantallaRegistro(navController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = "Registro image",
            modifier = Modifier.size(300.dp)
        )

//        Text(text = "Registrar Usuario", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Input de correo electrónico
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Correo Electrónico", fontSize = 24.sp) },
            textStyle = TextStyle(fontSize = 24.sp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Input de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Contraseña", fontSize = 24.sp) },
            textStyle = TextStyle(fontSize = 24.sp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirmar contraseña
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text(text = "Confirmar Contraseña", fontSize = 24.sp) },
            textStyle = TextStyle(fontSize = 24.sp),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para registrar el usuario
        Button(
            onClick = {
                if (password == confirmPassword) {
                    // Crear usuario con Firebase Authentication
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Obtener el usuario recién registrado
                                val user = auth.currentUser

                                // Guardar los datos del usuario en Firestore
                                val userData = hashMapOf(
                                    "uid" to user?.uid,
                                    "email" to user?.email,
                                    "provider" to "Email/Password",
                                    "creationDate" to System.currentTimeMillis().toString()
                                )

                                db.collection("usuarios").document(user?.uid!!)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Usuario registrado y guardado", Toast.LENGTH_SHORT).show()
                                        navController.navigate("login") // Navegar al login
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Error al guardar en Firestore", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(context, "Error al registrar en Firebase Auth", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(310.dp)
                .height(60.dp)
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),
            shape = RoundedCornerShape(12.dp)

        ) {
            Text(text = "Registrar", fontSize = 22.sp)
        }

        TextButton(onClick = { navController.navigate("login") }) {
            Text(text = "¿Ya tienes cuenta? Inicia Sesión", fontSize = 18.sp, color = Color.DarkGray)
        }

        Spacer(modifier = Modifier.weight(1f))

    }
}
