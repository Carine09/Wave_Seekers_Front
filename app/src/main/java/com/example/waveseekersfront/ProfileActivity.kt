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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waveseekersfront.ui.theme.NeueMontrealMediumFontFamily
import com.example.waveseekersfront.ui.theme.NeueMontrealRegularFontFamily
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme

class ProfileActivity : ComponentActivity() {
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
                    DisplayProfileInfo()

                }
            }
        }
    }
}

/* -------------------HEADER--------------*/


@Composable
fun ProfileInfoHeaderSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "My Account",
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


/*---------------------BODY---------------------------*/

@Composable
fun DeleteAccountButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column () {
        Row (
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ){

            Button(
                onClick = { onClick() },
                modifier = Modifier,
                shape = RoundedCornerShape(5.dp),
                colors= ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),

                ) {
                Text(
                    "Delete my account",
                    fontFamily = NeueMontrealMediumFontFamily,
                    color = MaterialTheme.colorScheme.surface,

                    )
            }
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = "Warning! This action is irreversible.",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 4.dp)
            )
        }


    }

}
@Composable
fun ChangeProfileInfo() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column {
        Text(
            text = "Email",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 4.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        )

        Text(
                text = "Password",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 4.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp)
        )

        SaveChangesButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            val intent = Intent(context, ProfileActivity::class.java)
            context.startActivity(intent)
        }
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            DeleteAccountButton() {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }
        }
    }
}
@Composable
fun SaveChangesButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(5.dp)) {
        Text(
            "Save changes",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.surface,

            )
    }
}


@Composable
fun DisplayProfileInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            ProfileInfoHeaderSection()
            ProfileInfoSeparator()
            ChangeProfileInfo()
        }
        ProfileInfoNavBar()
    }
}

//Horizontal buttons for changing pages in profile NOT WORKING.
/*
* @Composable
fun ProfileInfoSeparator(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val fieldWidth = 280.dp

    Column() {
        Row() {
            TextButton(
                onClick = {
                    val intent = Intent(context, CreateAccountActivity::class.java)
                    context.startActivity(intent)
                },
                modifier = modifier
                    .width(fieldWidth)
            ) {
                Text(
                    "Profile Info",
                    fontFamily = NeueMontrealMediumFontFamily,
                    color = MaterialTheme.colorScheme.secondary,
                )

                TextButton(
                    onClick = {
                        val intent = Intent(context, CreateAccountActivity::class.java)
                        context.startActivity(intent)
                    },
                    modifier = modifier
                        .width(fieldWidth)
                ) {
                    Text(
                        "Liked Spots",
                        fontFamily = NeueMontrealMediumFontFamily,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    TextButton(
                        onClick = {
                            val intent = Intent(context, CreateAccountActivity::class.java)
                            context.startActivity(intent)
                        },
                        modifier = modifier
                            .width(fieldWidth)
                    ) {
                        Text(
                            "Added Spots",
                            fontFamily = NeueMontrealMediumFontFamily,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                    }
                }
            }

}*/

/*---------------------NAVBAR-------------------------*/

@Composable
fun NavBarButtonProfileInfo(
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
fun ProfileInfoNavBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(horizontal = 24.dp)
    ) {
        NavBarButtonProfileInfo(
            iconRes = R.drawable.account_blue_icon,
            text = "Profile",
            iconContentDescription = "Profile icon",
            isActive = true,
            onClick = {
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            },
            modifier = Modifier.weight(1f)
        )
        NavBarButtonProfileInfo(
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
        NavBarButtonProfileInfo(
            iconRes = R.drawable.add_spot_blue_icon,
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

/*---------------------PREVIEWS -------------------*/


@Preview(showBackground = true)
@Composable
fun ProfileInfoHeaderPreview() {
    WaveSeekersFrontTheme {
        ProfileInfoHeaderSection(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ChangeProfileInfoPreview() {
    WaveSeekersFrontTheme {
        ChangeProfileInfo()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileInfoNavBarPreview(){
    WaveSeekersFrontTheme {
        ProfileInfoNavBar()
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayProfileInfoPreview() {
    WaveSeekersFrontTheme {
        DisplayProfileInfo(modifier = Modifier.padding(32.dp))
    }
}
