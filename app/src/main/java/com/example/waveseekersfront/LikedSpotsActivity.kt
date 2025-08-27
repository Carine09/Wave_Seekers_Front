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

class LikedSpotsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaveSeekersFrontTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisplayLikedSpots(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

/* -------------------HEADER--------------*/


@Composable
fun LikedSpotsHeaderSection(modifier: Modifier = Modifier) {
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
fun LikedSpotList(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Column(
        modifier = modifier

    ) {
        SpotCard(
            imageRes = R.drawable.oahu_spot_picture,
            spotName = "Oahu, Hawaii",
            country = "United States of America",
            imageContentDescription = "Oahu spot picture",
            difficultyLevel = 4,
            onClick = {
                val intent = Intent(context, SpotDetailsActivity::class.java).apply {
                    putExtra("SPOT_ID", "oahu")
                    putExtra("SPOT_NAME", "Oahu, Hawaii")
                    putExtra("COUNTRY", "United States of America")
                    putExtra("IMAGE_RES", R.drawable.oahu_spot_picture)
                    putExtra("DIFFICULTY_LEVEL", 4)
                    putExtra("PEAK_SEASON_START", "07-22")
                    putExtra("PEAK_SEASON_END", "08-31")
                    putExtra("GPS_COORDINATES", "21° 28′ 00″ N, 157° 59′ 00″ O")
                    putExtra("SURFING_CULTURE", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc in lorem id est vulputate vehicula et vel odio. In hac habitasse platea dictumst. Nullam iaculis dignissim orci, id fringilla dolor mollis vel. In vitae convallis felis. Vivamus eget dui at tortor mattis dapibus. Sed congue tortor dolor. Nam leo quam, pellentesque at vulputate sed, lacinia ut erat. Vivamus rhoncus scelerisque eros, a sollicitudin ipsum commodo quis. Etiam porttitor purus nibh, eget euismod augue semper in.")
                }
                context.startActivity(intent)
            }
        )
        SpotCard(
            imageRes = R.drawable.superbank_spot_picture,
            spotName = "Superbank, Gold Coast",
            country = "Australia",
            imageContentDescription = "Superbank spot picture",
            difficultyLevel = 4,
            onClick = {
                val intent = Intent(context, SpotDetailsActivity::class.java).apply {
                    putExtra("SPOT_ID", "superbank")
                    putExtra("SPOT_NAME", "Superbank, Gold Coast")
                    putExtra("COUNTRY", "Australia")
                    putExtra("IMAGE_RES", R.drawable.superbank_spot_picture)
                    putExtra("DIFFICULTY_LEVEL", 4)
                    putExtra("PEAK_SEASON_START", "11-28")
                    putExtra("PEAK_SEASON_END", "02-01")
                    putExtra("GPS_COORDINATES", "28° 10′ 00″ S, 153° 33′ 00″ E")
                    putExtra("SURFING_CULTURE", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc in lorem id est vulputate vehicula et vel odio. In hac habitasse platea dictumst. Nullam iaculis dignissim orci, id fringilla dolor mollis vel. In vitae convallis felis. Vivamus eget dui at tortor mattis dapibus. Sed congue tortor dolor. Nam leo quam, pellentesque at vulputate sed, lacinia ut erat. Vivamus rhoncus scelerisque eros, a sollicitudin ipsum commodo quis. Etiam porttitor purus nibh, eget euismod augue semper in.")
                }
                context.startActivity(intent)
            }
        )

    }
}
@Composable
fun DisplayLikedSpots(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            LikedSpotsHeaderSection()
            ProfileInfoSeparator()
            LikedSpotList()

        }
        LikedSpotsNavBar()
    }
}


/*---------------------NAVBAR-------------------------*/

@Composable
fun NavBarButtonLikedSpots(
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
fun LikedSpotsNavBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(horizontal = 24.dp)
    ) {
        NavBarButtonLikedSpots(
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
        NavBarButtonLikedSpots(
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
        NavBarButtonLikedSpots(
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
fun LikedSpotsHeaderPreview() {
    WaveSeekersFrontTheme {
        LikedSpotsHeaderSection(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LikedSpotsNavBarPreview(){
    WaveSeekersFrontTheme {
        LikedSpotsNavBar()
    }
}

@Preview(showBackground = true)
@Composable
fun DisplayLikedSpotsPreview() {
    WaveSeekersFrontTheme {
        DisplayLikedSpots(modifier = Modifier.padding(32.dp))
    }
}
