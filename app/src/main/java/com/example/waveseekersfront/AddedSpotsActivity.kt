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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

fun AddedSpotList(userSpots: List<Spot>, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        userSpots.forEach { spot ->
            SpotCard(
                imageRes = spot.imageRes,
                spotName = spot.spotName,
                country = spot.country,
                imageContentDescription = spot.imageContentDescription,
                difficultyLevel = spot.difficultyLevel,
                onClick = {
                    val intent = Intent(context, SpotDetailsActivity::class.java).apply {
                        putExtra("SPOT_ID", spot.id)
                        putExtra("SPOT_NAME", spot.spotName)
                        putExtra("COUNTRY", spot.country)
                        putExtra("IMAGE_RES", spot.imageRes)
                        putExtra("DIFFICULTY_LEVEL", spot.difficultyLevel)
                        putExtra("PEAK_SEASON_START", spot.peakSeasonStart)
                        putExtra("PEAK_SEASON_END", spot.peakSeasonEnd)
                        putExtra("GPS_COORDINATES", spot.gpsCoordinates)
                        putExtra("SURFING_CULTURE", spot.surfingCulture)
                    }
                    context.startActivity(intent)
                }
            )
        }
    }
}
@Composable
fun DisplayAddedSpots(modifier: Modifier = Modifier) {
    var userSpots by remember { mutableStateOf<List<Spot>>(emptyList()) }

    LaunchedEffect(Unit) {
        val (apiSpots, apiCountries) = withContext(Dispatchers.IO) {
            val spots = ApiService.fetchSpotsByUser(1) //user_ud : 1 to mock the connected version
            val countries = ApiService.fetchCountries()
            Pair(spots, countries)
        }
        if (apiSpots != null && apiCountries != null) {
            userSpots = apiSpots.map { it.toUiSpot() }
        }
    }
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
            AddedSpotList(userSpots = userSpots)

        }
        AddedSpotsNavBar()
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
