import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a02appsensorialauditivav2.R
import java.util.*

@Composable
fun TextToSpeechScreen(context: Context) {
    var text by remember { mutableStateOf("") }
    var tts by remember { mutableStateOf<TextToSpeech?>(null) }

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

        Text(text = "Texto a Voz", style = MaterialTheme.typography.headlineSmall)


        // Usamos DisposableEffect para manejar el ciclo de vida del TextToSpeech
        DisposableEffect(Unit) {
            tts = TextToSpeech(context) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    tts?.language =
                        Locale("es", "ES")
                }
            }

            // Cleanup cuando el Composable es destruido
            onDispose {
                tts?.stop()
                tts?.shutdown()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Ingresa el texto") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                },
                modifier = Modifier
                    .width(310.dp)
                    .height(60.dp)
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Reproducir Texto", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

        }
    }
}
