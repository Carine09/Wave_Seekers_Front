package com.example.waveseekersfront

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.waveseekersfront.ui.theme.NeueMontrealMediumFontFamily

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp


class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaveSeekersFrontTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Login()
                    GoToCreateAccountActivity()
                }
            }
        }
    }
}

@Composable
fun Login() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val fieldWidth = 280.dp

    Column {
        Image(
            painter = painterResource(R.drawable.wave_seekers_main_logo_light),
            contentDescription = "Main wave seekers logo",
            modifier = Modifier
                .size(280.dp)
                .width(fieldWidth)
        )
        Text(
            text = "Email",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.primary,
        )
        OutlinedTextField(
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            ),
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            modifier = Modifier
                .width(fieldWidth)
                .padding(top = 10.dp)
        )
        Text(
            text = "Password",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(top = 18.dp)
        )
        OutlinedTextField(
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            ),
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            modifier = Modifier
                .width(fieldWidth)
                .padding(top = 10.dp),
            visualTransformation = PasswordVisualTransformation('\u002A'),
        )

        LoginButton (modifier = Modifier
            .width(fieldWidth)
            .padding(top = 20.dp)){
            val intent = Intent(context, SpotListActivity::class.java)
            context.startActivity(intent)
        }


    }
}

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(5.dp)) {
        Text(
            "Login",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.surface,
        )
    }
}

@Composable
fun GoToCreateAccountActivity(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val fieldWidth = 280.dp

    TextButton(
        onClick = {
            val intent = Intent(context, CreateAccountActivity::class.java)
            context.startActivity(intent) },
        modifier = modifier
            .width(fieldWidth)) {
        Text(
            "Ready to find epic spots? Join the wave seeking community here!",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}


@Preview (showBackground = true)
@Composable
fun LoginPreview() {
    WaveSeekersFrontTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Login()
            GoToCreateAccountActivity()
        }
    }
}