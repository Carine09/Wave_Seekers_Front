package com.example.waveseekersfront

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waveseekersfront.ui.theme.NeueMontrealMediumFontFamily
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme

class NewSpotActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaveSeekersFrontTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisplayNewSpotForm(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun AddSpotHeaderSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Add Spot",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        )
        Image(
            painter = painterResource(R.drawable.wave_seekers_secondary_logo_light),
            contentDescription = "Secondary wave seekers logo",
            modifier = Modifier
                .padding(bottom = 8.dp)
        )
    }
}

@Composable
fun AddSpotContent() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val fieldWidth = 280.dp

    Column {
        Text(
            text = "Spot's name",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.primary,
        )
        OutlinedTextField(
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
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            modifier = Modifier
                .width(fieldWidth)
                .padding(top = 10.dp)
        )

        SubmitButton(
            modifier = Modifier
                .width(fieldWidth)
                .padding(top = 20.dp)
        ) {
            val intent = Intent(context, SpotListActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(5.dp)) {
        Text(
            "Submit New Spot",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.surface,
        )
    }
}

@Composable
fun NavBarButtonAddSpot(
    iconRes: Int,
    text: String,
    iconContentDescription: String,
    isActive: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = iconContentDescription,
            modifier = Modifier.height(20.dp)
        )
        Text(
            text = text,
            fontFamily = NeueMontrealMediumFontFamily,
            color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
            fontSize = 10.sp
        )
    }
}

@Composable
fun BottomNavBarAddSpot(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(horizontal = 24.dp)
    ) {
        NavBarButtonAddSpot(
            iconRes = R.drawable.logout_grey_icon,
            text = "Log out",
            iconContentDescription = "Log out icon",
            isActive = false,
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            },
            modifier = Modifier.weight(1f)
        )
        NavBarButtonAddSpot(
            iconRes = R.drawable.home_grey_icon,
            text = "Home",
            iconContentDescription = "Home icon",
            isActive = false,
            onClick = {
                val intent = Intent(context, SpotListActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.weight(1f)
        )
        NavBarButtonAddSpot(
            iconRes = R.drawable.add_spot_grey_icon,
            text = "Add a spot",
            iconContentDescription = "Add a spot icon",
            isActive = false,
            onClick = {
                val intent = Intent(context, NewSpotActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DisplayNewSpotForm(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            AddSpotHeaderSection()
            AddSpotContent()
        }
        BottomNavBarAddSpot()
    }
}

@Preview(showBackground = true)
@Composable
fun AddSpotHeaderPreview(){
    WaveSeekersFrontTheme {
        AddSpotHeaderSection(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun NewSpotPreview() {
    WaveSeekersFrontTheme {
        DisplayNewSpotForm()
    }
}