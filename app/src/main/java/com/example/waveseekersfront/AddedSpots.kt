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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waveseekersfront.ui.theme.NeueMontrealMediumFontFamily
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme

class AddedSpotsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaveSeekersFrontTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisplayAddedSpots(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

/* -------------------HEADER--------------*/


@Composable
fun AddedSpotsHeaderSection(modifier: Modifier = Modifier) {
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
fun AddedSpotList(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier

    ) {
        SpotCard(
            imageRes = R.drawable.superbank_spot_picture,
            spotName = "Superbank, Gold Coast",
            country = "Australia",
            imageContentDescription = "Superbank spot picture",
            difficultyLevel = 4,
            onClick = {
                val intent = Intent(context, SpotDetailsActivity::class.java).apply {
                    putExtra("SPOT_ID", "manubay")
                    putExtra("SPOT_NAME", "Manu Bay, Raglan")
                    putExtra("COUNTRY", "New Zealand")
                    putExtra("IMAGE_RES", R.drawable.manubay_spot_picture)
                    putExtra("DIFFICULTY_LEVEL", 2)
                    putExtra("PEAK_SEASON_START", "12-01")
                    putExtra("PEAK_SEASON_END", "01-31")
                    putExtra("GPS_COORDINATES", "37° 39′ 00″ S, 174° 45′ 00″ E")
                    putExtra("SURFING_CULTURE", "Manu Bay gained international fame after featuring in the 1966 surf film The Endless Summer, introducing the world to New Zealand's pristine left-hand point break. The wave breaks consistently along a rocky coastline near Raglan, creating long, workable walls perfect for traditional longboard surfing and modern shortboard performance. New Zealand's surfing culture at Manu Bay reflects the country's laid-back, environmentally conscious ethos, with strong emphasis on preserving the natural coastline. Local Maori connections to the ocean add cultural depth to the surfing experience, respecting indigenous relationships with the sea. The spot remains relatively uncrowded compared to other world-class breaks, maintaining its appeal as a peaceful surfing sanctuary in the Southern Hemisphere.")
                }
                context.startActivity(intent)
            }
        )

    }
}
@Composable
fun DisplayAddedSpots(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            AddedSpotsHeaderSection()
            ProfileInfoSeparator()
            HorizontalDivider()
            AddedSpotList()

        }
        AddedSpotsNavBar()
    }
}

@Composable
fun ProfileInfoButton(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text("Profile info")
    }
}

@Composable
fun LikedSpotButton(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text("Liked Spots",
            fontFamily = NeueMontrealMediumFontFamily,
            //color = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
fun AddedSpotButton(onClick: () -> Unit) {
    TextButton(
        onClick = { onClick() }
    ) {
        Text("Added Spots")
    }
}



/*---------------------NAVBAR-------------------------*/

@Composable
fun NavBarButtonAddedSpots(
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
fun AddedSpotsNavBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(horizontal = 24.dp)
    ) {
        NavBarButtonAddedSpots(
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
        NavBarButtonAddedSpots(
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
        NavBarButtonAddedSpots(
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
fun AddedSpotsHeaderPreview() {
    WaveSeekersFrontTheme {
        AddedSpotsHeaderSection(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AddedSpotsNavBarPreview(){
    WaveSeekersFrontTheme {
        AddedSpotsNavBar()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileInfoSeparatorPreview(){
    WaveSeekersFrontTheme {
        ProfileInfoSeparator()
    }
}


@Preview(showBackground = true)
@Composable
fun DisplayAddedSpotsPreview() {
    WaveSeekersFrontTheme {
        DisplayAddedSpots(modifier = Modifier.padding(32.dp))
    }
}
