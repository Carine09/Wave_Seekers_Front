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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.waveseekersfront.ui.theme.NeueMontrealBoldFontFamily
import com.example.waveseekersfront.ui.theme.NeueMontrealMediumFontFamily
import com.example.waveseekersfront.ui.theme.NeueMontrealRegularFontFamily
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaveSeekersFrontTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisplayProfileInfo(
                        modifier = Modifier.padding(innerPadding),
                    )
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
    Column (modifier = Modifier
        .padding(top = 100.dp)
    ) {
        Row (
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ){

            Button(
                onClick = { onClick() },
                modifier = Modifier
                    .padding(vertical = 5.dp),
                shape = RoundedCornerShape(5.dp),
                colors= ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),

                ) {
                Text(
                    "Delete my account",
                    fontFamily = NeueMontrealBoldFontFamily,
                    color = MaterialTheme.colorScheme.surface,

                    )
            }
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start,
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
                .padding(start = 4.dp, top = 16.dp)
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
fun LogOutButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(5.dp),
        colors= ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onTertiary),
    ) {
        Text(
            "Log out",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun ProfileInfoButton(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text("Profile info",
            fontFamily = NeueMontrealMediumFontFamily,
        )
    }
}

@Composable
fun LikedSpotButton(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text("Liked Spots",
            fontFamily = NeueMontrealMediumFontFamily,
        )
    }
}

@Composable
fun AddedSpotButton(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text("Added Spots",
            fontFamily = NeueMontrealMediumFontFamily,
        )
    }
}

@Composable
fun ProfileInfoSeparator(){
    val context = LocalContext.current
    Column() {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            LogOutButton() {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            ProfileInfoButton(onClick = {
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)

            })
            LikedSpotButton(onClick = {
                val intent = Intent(context, LikedSpotsActivity::class.java)
                context.startActivity(intent)

            })
            AddedSpotButton(onClick = {
                val intent = Intent(context, AddedSpotsActivity::class.java)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            })
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
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
