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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DisplayLikedSpots()

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
                    putExtra("GPS_COORDINATES", "21° 28′ 00″ N, 157° 59′ 00″ W")
                    putExtra("SURFING_CULTURE", "Oahu's North Shore represents the spiritual birthplace of modern surfing, where ancient Polynesian chiefs rode waves as a sacred practice called he'e nalu over a thousand years ago. The Pipeline break, discovered in the 1960s, became the ultimate proving ground for professional surfers, with its perfect but deadly barrels claiming both legends and lives. Hawaiian surfers like Eddie Aikau and the Da Silva family established a culture of respect, courage, and deep ocean knowledge that influences surfing worldwide. The area hosts the world's most prestigious surfing competitions, including the Vans Triple Crown, where careers are made and broken each winter. Today, the North Shore maintains its position as surfing's most revered arena, where local Hawaiian values of aloha and respect for the ocean remain paramount.")
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
                    putExtra("SURFING_CULTURE", "The Superbank was artificially created in the 1990s through a massive sand pumping project that transformed three separate breaks into one continuous 2-kilometer wave. This engineering feat revolutionized professional surfing by creating the world's most consistent high-performance wave, hosting the annual World Surf League Championship Tour event. Australian surfing culture thrives here, with local groms learning alongside world champions in a uniquely egalitarian lineup. The spot embodies Australia's progressive approach to wave riding, where technical innovation and competitive excellence drive the culture forward. Despite being man-made, the Superbank has produced more perfect 10-point rides in professional surfing than any other wave, cementing its status as a modern surfing marvel.")
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
