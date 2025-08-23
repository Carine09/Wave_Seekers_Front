package com.example.waveseekersfront

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
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.waveseekersfront.ui.theme.NeueMontrealMediumFontFamily

import android.content.Intent
import androidx.compose.ui.platform.LocalContext

class LoginActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaveSeekersFrontTheme {
                Login()
            }
        }
    }
}

@Composable
fun Login() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column {
        Image(
            painter = painterResource(R.drawable.wave_seekers_main_logo_light),
            contentDescription = "Main wave seekers logo"
        )
        Text(
            text = "Email",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.primary,
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true
        )
        Text(
            text = "Password",
            fontFamily = NeueMontrealMediumFontFamily
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true
        )

        LoginButton {
            val intent = Intent(context, SpotListActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text(
            "Login",
            fontFamily = NeueMontrealMediumFontFamily
        )
    }
}

@Composable
fun GoToCreateAccountActivity() {
}


@Preview (showBackground = true)
@Composable
fun LoginPreview() {
    Column {
        Login()
    }
}