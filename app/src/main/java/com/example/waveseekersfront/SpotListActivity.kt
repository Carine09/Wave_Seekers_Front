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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.google.gson.annotations.SerializedName

/* TBD : refactoring : to be moved in a Spot.kt file */

// API data classes
data class ApiSpot(
    val id: Int,
    val user_id: Int,
    val country_id: Int,
    val country_name: String,
    val destination: String,
    val location: String,
    val lat: Double,
    val long: Double,
    val peak_season_start: String,
    val peak_season_end: String,
    val difficulty_level: Int,
    val surfing_culture: String,
    val image_url: String
)

data class ApiCountry(
    @SerializedName("ID") val id: Int,
    @SerializedName("Name") val name: String
)

// Dynamic converter function
fun ApiSpot.toUiSpot(): Spot {
    return Spot(
        id = this.id.toString(),
        imageRes = getImageResourceForDestination(this.destination),
        spotName = "${this.destination}, ${this.location}",
        country = this.country_name, // ✅ maintenant direct
        imageContentDescription = "${this.destination} spot picture",
        difficultyLevel = this.difficulty_level,
        peakSeasonStart = this.peak_season_start,
        peakSeasonEnd = this.peak_season_end,
        gpsCoordinates = "${this.lat}, ${this.long}",
        surfingCulture = this.surfing_culture
    )
}

// Image mapping
private fun getImageResourceForDestination(destination: String): Int {
    return when (destination.lowercase()) {
        "oahu north shore" -> R.drawable.oahu_spot_picture
        "skeleton bay" -> R.drawable.skeletonbay_spot_picture
        "superbank" -> R.drawable.superbank_spot_picture
        "manu bay" -> R.drawable.manubay_spot_picture
        "playa chicama" -> R.drawable.chicama_spot_picture
        "the bubble" -> R.drawable.thebubble_spot_picture
        "pasta point" -> R.drawable.pastapoint_spot_picture
        "supertubes beach" -> R.drawable.jeffreysbay_spot_picture
        "kitty hawk" -> R.drawable.kittyhawk_spot_picture
        "rockaway beach" -> R.drawable.rockaway_spot_picture
        else -> R.drawable.oahu_spot_picture // Default image for new spots
    }
}

/* END ! refactoring : to be moved in a Spot.kt file */

data class Spot(
    val id: String,
    val imageRes: Int,
    val spotName: String,
    val country: String,
    val imageContentDescription: String,
    val difficultyLevel: Int,
    val peakSeasonStart: String,
    val peakSeasonEnd: String,
    val gpsCoordinates: String,
    val surfingCulture: String
)

class SpotListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaveSeekersFrontTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisplaySpotList(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun HomeHeaderSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Find your wave",
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
fun ResearchBar(query: String,
                onQueryChange: (String) -> Unit,
                modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
        ),
            trailingIcon = {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                ) },
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            label = {
                Text("Looking for spots in a specific country?",
                    fontFamily = NeueMontrealRegularFontFamily,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

    }
}

@Composable
fun SpotCard(
    imageRes: Int,
    spotName: String,
    country: String,
    imageContentDescription: String,
    difficultyLevel: Int = 1,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = imageContentDescription,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Row {
            Text(
                text = spotName,
                fontFamily = NeueMontrealBoldFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
            Text(
                text = " | $country",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Difficulty level :",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 12.sp,
                modifier = Modifier.padding(end = 4.dp)
            )

            repeat(5) { index ->
                Image(
                    painter = painterResource(
                        if (index < difficultyLevel) {
                            R.drawable.difficulty_star_lightblue_full_icon
                        } else {
                            R.drawable.difficulty_star_lightblue_outline_icon
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
    }
}

@Composable
fun NavBarButtonHome(
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
fun BottomNavBarHome(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(horizontal = 24.dp)
    ) {
        NavBarButtonHome(
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
        NavBarButtonHome(
            iconRes = R.drawable.home_blue_icon,
            text = "Home",
            iconContentDescription = "Home icon",
            isActive = true,
            modifier = Modifier.weight(1f)
        )
        NavBarButtonHome(
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
fun DisplaySpotList(modifier: Modifier = Modifier) {
    var query by rememberSaveable { mutableStateOf("") }
    var spots by remember { mutableStateOf<List<Spot>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    // Fetch both spots and countries from API
    LaunchedEffect(Unit) {
        try {
            val (apiSpots, apiCountries) = withContext(Dispatchers.IO) {
                val spots = ApiService.fetchSpotList()
                val countries = ApiService.fetchCountries()
                Pair(spots, countries)
            }

            if (apiSpots != null) {
                spots = apiSpots.map { it.toUiSpot()}
            } else {
                errorMessage = "Failed to load data from server"
                Log.e("API_ERROR", "Failed to fetch spots or countries")
            }
        } catch (e: Exception) {
            errorMessage = "Error: ${e.message}"
            Log.e("API_ERROR", "Exception while fetching data", e)
        } finally {
            isLoading = false
        }
    }

    val filteredSpots = spots.filter {
        it.country.contains(query, ignoreCase = true) ||
                it.spotName.contains(query, ignoreCase = true)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            HomeHeaderSection()
            ResearchBar(
                query = query,
                onQueryChange = { query = it }
            )

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage ?: "Unknown error",
                            color = MaterialTheme.colorScheme.error,
                            fontFamily = NeueMontrealRegularFontFamily
                        )
                    }
                }
                filteredSpots.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No spots found",
                            fontFamily = NeueMontrealRegularFontFamily
                        )
                    }
                }
                else -> {
                    filteredSpots.forEach { spot ->
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
        }
        BottomNavBarHome()
    }
}

@Preview(showBackground = true)
@Composable
fun HomeHeaderPreview() {
    WaveSeekersFrontTheme {
        HomeHeaderSection(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SpotCardPreview() {
    WaveSeekersFrontTheme {
        SpotCard(
            imageRes = R.drawable.oahu_spot_picture,
            spotName = "Oahu, Hawaii",
            country = "United States of America",
            imageContentDescription = "Oahu spot picture",
            difficultyLevel = 4,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SpotListContentPreview() {
    WaveSeekersFrontTheme {
        DisplaySpotList(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    WaveSeekersFrontTheme {
        BottomNavBarHome(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SpotListPreview() {
    WaveSeekersFrontTheme {
        DisplaySpotList()
    }
}
