package com.example.waveseekersfront

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.waveseekersfront.ui.theme.NeueMontrealMediumFontFamily
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaveSeekersFrontTheme {
                LoadingScreen(onLoadingFinished = {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                })
            }
        }
    }
}


@Composable
fun LoadingScreen(onLoadingFinished: () -> Unit) {

    LaunchedEffect(Unit) {
        try {
            val message = withContext(kotlinx.coroutines.Dispatchers.IO) {
                ApiService.fetchHelloMessage()
            }
            // Affiche dans le logcat
            Log.d("API_TEST", "Message reçu du back: $message")
        } catch (e: Exception) {
            Log.e("API_TEST", "Erreur lors de la récupération du message", e)
        }

        delay(2000)
        onLoadingFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.wave_seekers_main_logo_light),
                contentDescription = "Main wave seekers logo",
                modifier = Modifier.size(240.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Surfin' into the app...",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
            GifImage(modifier = Modifier.size(100.dp))
        }
    }
}

@Composable
fun GifImage(modifier: Modifier = Modifier) {
    val context = LocalContext.current


    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(R.drawable.loading_gif)
            .decoderFactory { result, options, _ ->
                coil.decode.ImageDecoderDecoder(result.source, options)
            }
            .build()
    )
    Image(
        painter = painter,
        contentDescription = "Animated GIF",
        modifier = modifier
    )

}

@Preview(showBackground = true)
@Composable
fun LoadingPreview() {
    WaveSeekersFrontTheme {
        LoadingScreen(onLoadingFinished = {})
    }
}