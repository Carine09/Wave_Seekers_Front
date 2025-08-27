package com.example.waveseekersfront

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waveseekersfront.ui.theme.NeueMontrealBoldFontFamily
import com.example.waveseekersfront.ui.theme.NeueMontrealMediumFontFamily
import com.example.waveseekersfront.ui.theme.NeueMontrealRegularFontFamily
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme

class SpotDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val spotName = intent.getStringExtra("SPOT_NAME") ?: "Unknown Spot"
        val country = intent.getStringExtra("COUNTRY") ?: "Unknown Country"
        val imageRes = intent.getIntExtra("IMAGE_RES", R.drawable.oahu_spot_picture)
        val difficultyLevel = intent.getIntExtra("DIFFICULTY_LEVEL", 1)
        val peakSeasonStart = intent.getStringExtra("PEAK_SEASON_START") ?: "Unknown"
        val peakSeasonEnd = intent.getStringExtra("PEAK_SEASON_END") ?: "Unknown"
        val gpsCoordinates = intent.getStringExtra("GPS_COORDINATES") ?: "Unknown"
        val surfingCulture = intent.getStringExtra("SURFING_CULTURE") ?: "No description available"

        setContent {
            WaveSeekersFrontTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisplaySpotDetails(
                        modifier = Modifier.padding(innerPadding),
                        spotName = spotName,
                        country = country,
                        imageRes = imageRes,
                        difficultyLevel = difficultyLevel,
                        peakSeasonStart = peakSeasonStart,
                        peakSeasonEnd = peakSeasonEnd,
                        gpsCoordinates = gpsCoordinates,
                        surfingCulture = surfingCulture
                    )
                }
            }
        }
    }
}

@Composable
fun SpotDetailsHeaderSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Spot details",
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
fun SpotDetailsCard(
    spotName: String,
    country: String,
    imageRes: Int,
    imageContentDescription: String,
    difficultyLevel: Int = 1,
    peakSeasonIcon : Int,
    peakSeasonIconDescription : String,
    peakSeasonTitle : String,
    peakSeasonStart : String,
    peakSeasonSeparator : String,
    peakSeasonEnd : String,
    gpsCoordinatesIcon : Int,
    gpsCoordinatesIconDescription : String,
    gpsCoordinatesTitle : String,
    gpsCoordinatesContent : String,
    surfingCultureTitle : String,
    surfingCultureContent : String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        // Spot name and country
        Row {
            Text(
                text = spotName,
                fontFamily = NeueMontrealBoldFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Text(
                text = " | $country",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }

        // Image
        Image(
            painter = painterResource(imageRes),
            contentDescription = imageContentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        // Difficulty level
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Difficulty level :",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 8.dp, end = 4.dp)
            )

            repeat(5) { index ->
                Image(
                    painter = painterResource(
                        if (index < difficultyLevel) {
                            R.drawable.difficulty_star_orange_full_icon
                        } else {
                            R.drawable.difficulty_star_orange_outline_icon
                        }
                    ),
                    contentDescription = if (index < difficultyLevel) {
                        "Full difficulty star"
                    } else {
                        "Empty difficulty star"
                    },
                    modifier = Modifier.height(20.dp)
                )
            }
        }

        // Peak season
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(peakSeasonIcon),
                contentDescription = peakSeasonIconDescription,
                modifier = Modifier.height(20.dp)
            )
            Text(
                text = peakSeasonTitle,
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 4.dp, top = 8.dp)
            )
            Text(
                text = peakSeasonStart,
                fontFamily = NeueMontrealRegularFontFamily,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 4.dp, top = 8.dp)
            )
            Text(
                text = peakSeasonSeparator,
                fontFamily = NeueMontrealRegularFontFamily,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 4.dp, top = 8.dp)
            )
            Text(
                text = peakSeasonEnd,
                fontFamily = NeueMontrealRegularFontFamily,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 4.dp, top = 8.dp)
            )
        }

        // GPS coordinates
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(gpsCoordinatesIcon),
                contentDescription = gpsCoordinatesIconDescription,
                modifier = Modifier.height(20.dp)
            )
            Text(
                text = gpsCoordinatesTitle,
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 4.dp, top = 8.dp)
            )
            Text(
                text = gpsCoordinatesContent,
                fontFamily = NeueMontrealRegularFontFamily,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(start = 4.dp, top = 8.dp)
            )
        }

        // Surfing culture
        Text(
            text = surfingCultureTitle,
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        Text(
            text = surfingCultureContent,
            fontFamily = NeueMontrealRegularFontFamily,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
        )
    }
}

@Composable
fun NavBarButtonDetails(
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
fun BottomNavBarDetails(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(horizontal = 24.dp)
    ) {
        NavBarButtonDetails(
            iconRes = R.drawable.account_grey_icon,
            text = "Profile",
            iconContentDescription = "Profile icon",
            isActive = false,
            onClick = {
                val intent = Intent(context, ProfileActivity::class.java)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            },
            modifier = Modifier.weight(1f)
        )
        NavBarButtonDetails(
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
        NavBarButtonDetails(
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
fun DisplaySpotDetails(
    modifier: Modifier = Modifier,
    spotName: String,
    country: String,
    imageRes: Int,
    difficultyLevel: Int,
    peakSeasonStart: String,
    peakSeasonEnd: String,
    gpsCoordinates: String,
    surfingCulture: String
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            SpotDetailsHeaderSection()
            SpotDetailsCard(
                spotName = spotName,
                country = country,
                imageRes = imageRes,
                imageContentDescription = "$spotName spot picture",
                difficultyLevel = difficultyLevel,
                peakSeasonIcon = R.drawable.peak_season_blue_icon,
                peakSeasonIconDescription = "Calendar blue icon",
                peakSeasonTitle = "Peak season :",
                peakSeasonStart = peakSeasonStart,
                peakSeasonSeparator = "|",
                peakSeasonEnd = peakSeasonEnd,
                gpsCoordinatesIcon = R.drawable.gps_coordinates_blue_icon,
                gpsCoordinatesIconDescription = "Location pin blue icon",
                gpsCoordinatesTitle = "GPS coordinates :",
                gpsCoordinatesContent = gpsCoordinates,
                surfingCultureTitle = "Surfing culture :",
                surfingCultureContent = surfingCulture
            )
        }
        BottomNavBarDetails()
    }
}

@Preview(showBackground = true)
@Composable
fun SpotDetailsHeaderPreview() {
    WaveSeekersFrontTheme {
        SpotDetailsHeaderSection(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SpotDetailsPreview() {
    WaveSeekersFrontTheme {
        DisplaySpotDetails(
            spotName = "Oahu, Hawaii",
            country = "United States of America",
            imageRes = R.drawable.oahu_spot_picture,
            difficultyLevel = 4,
            peakSeasonStart = "07-22",
            peakSeasonEnd = "08-31",
            gpsCoordinates = "21° 28′ 00″ N, 157° 59′ 00″ O",
            surfingCulture = "Sample culture description"
        )
    }
}