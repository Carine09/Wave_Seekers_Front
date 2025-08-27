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

val allSpots = listOf(
    Spot(
        id = "oahu",
        imageRes = R.drawable.oahu_spot_picture,
        spotName = "Oahu, Hawaii",
        country = "USA",
        imageContentDescription = "Oahu spot picture",
        difficultyLevel = 4,
        peakSeasonStart = "07-22",
        peakSeasonEnd = "08-31",
        gpsCoordinates = "21° 28′ 00″ N, 157° 59′ 00″ W",
        surfingCulture = "Oahu's North Shore represents the spiritual birthplace of modern surfing, where ancient Polynesian chiefs rode waves as a sacred practice called he'e nalu over a thousand years ago. The Pipeline break, discovered in the 1960s, became the ultimate proving ground for professional surfers, with its perfect but deadly barrels claiming both legends and lives. Hawaiian surfers like Eddie Aikau and the Da Silva family established a culture of respect, courage, and deep ocean knowledge that influences surfing worldwide. The area hosts the world's most prestigious surfing competitions, including the Vans Triple Crown, where careers are made and broken each winter. Today, the North Shore maintains its position as surfing's most revered arena, where local Hawaiian values of aloha and respect for the ocean remain paramount."
    ),
    Spot(
        id = "skeleton_bay",
        imageRes = R.drawable.skeletonbay_spot_picture,
        spotName = "Skeleton Bay",
        country = "Namibia",
        imageContentDescription = "Skeleton Bay spot picture",
        difficultyLevel = 5,
        peakSeasonStart = "09-01",
        peakSeasonEnd = "11-30",
        gpsCoordinates = "22° 59′ 00″ S, 14° 30′ 00″ E",
        surfingCulture = "Skeleton Bay emerged from obscurity in the early 2000s when surf explorers discovered this remote left-hand point break along Namibia's desolate coast. The wave breaks over shallow sandbanks created by the Orange River's sediment deposits, producing rides that can last over a minute across multiple sections. Its isolation in the Namib Desert, accessible only by 4WD vehicles, has preserved a raw, adventurous surfing culture reminiscent of early surf exploration. Professional surfers began pilgrimage-like trips to this spot, often camping in harsh desert conditions for weeks to score perfect sessions. The break has become legendary for producing some of the longest barrel rides on Earth, attracting only the most dedicated wave hunters willing to endure extreme conditions."
    ),
    Spot(
        id = "superbank",
        imageRes = R.drawable.superbank_spot_picture,
        spotName = "Superbank, Gold Coast",
        country = "Australia",
        imageContentDescription = "Superbank spot picture",
        difficultyLevel = 4,
        peakSeasonStart = "11-28",
        peakSeasonEnd = "02-01",
        gpsCoordinates = "28° 10′ 00″ S, 153° 33′ 00″ E",
        surfingCulture = "The Superbank was artificially created in the 1990s through a massive sand pumping project that transformed three separate breaks into one continuous 2-kilometer wave. This engineering feat revolutionized professional surfing by creating the world's most consistent high-performance wave, hosting the annual World Surf League Championship Tour event. Australian surfing culture thrives here, with local groms learning alongside world champions in a uniquely egalitarian lineup. The spot embodies Australia's progressive approach to wave riding, where technical innovation and competitive excellence drive the culture forward. Despite being man-made, the Superbank has produced more perfect 10-point rides in professional surfing than any other wave, cementing its status as a modern surfing marvel."
    ),
    Spot(
        id = "manubay",
        imageRes = R.drawable.manubay_spot_picture,
        spotName = "Manu Bay, Raglan",
        country = "New Zealand",
        imageContentDescription = "Manu Bay spot picture",
        difficultyLevel = 2,
        peakSeasonStart = "12-01",
        peakSeasonEnd = "01-31",
        gpsCoordinates = "37° 39′ 00″ S, 174° 45′ 00″ E",
        surfingCulture = "Manu Bay gained international fame after featuring in the 1966 surf film The Endless Summer, introducing the world to New Zealand's pristine left-hand point break. The wave breaks consistently along a rocky coastline near Raglan, creating long, workable walls perfect for traditional longboard surfing and modern shortboard performance. New Zealand's surfing culture at Manu Bay reflects the country's laid-back, environmentally conscious ethos, with strong emphasis on preserving the natural coastline. Local Maori connections to the ocean add cultural depth to the surfing experience, respecting indigenous relationships with the sea. The spot remains relatively uncrowded compared to other world-class breaks, maintaining its appeal as a peaceful surfing sanctuary in the Southern Hemisphere."
    ),
    Spot(
        id = "playachicama",
        imageRes = R.drawable.chicama_spot_picture,
        spotName = "Playa Chicama, Lima",
        country = "Peru",
        imageContentDescription = "Playa Chicama spot picture",
        difficultyLevel = 3,
        peakSeasonStart = "05-01",
        peakSeasonEnd = "06-28",
        gpsCoordinates = "7° 51′ 00″ S, 79° 26′ 00″ W",
        surfingCulture = "Chicama boasts the world's longest left-hand wave, with rides potentially lasting over two minutes and covering more than two kilometers when conditions align. Ancient Peruvian civilizations, including the Moche people, used reed boats called caballitos de totora for fishing and wave riding over 3,000 years ago, predating Polynesian surf culture. The modern surf scene developed in the 1960s when international surfers discovered this remote northern Peru gem, accessible only by boat or challenging overland routes. Peruvian surfing culture here blends indigenous maritime traditions with contemporary wave riding, creating a unique atmosphere of respect for ancient ocean wisdom. The break's extreme length and power demand patience and skill, attracting surfers seeking the ultimate point break experience in South America."
    ),
    Spot(
        id = "bubble",
        imageRes = R.drawable.thebubble_spot_picture,
        spotName = "The Bubble, Fuerteventura",
        country = "Canary Islands",
        imageContentDescription = "The Bubble spot picture",
        difficultyLevel = 3,
        peakSeasonStart = "06-01",
        peakSeasonEnd = "09-01",
        gpsCoordinates = "28° 21′ 00″ N, 14° 02′ 00″ W",
        surfingCulture = "Fuerteventura's surf culture emerged in the 1970s when European surfers discovered consistent Atlantic swells wrapping around volcanic reefs and sand bottoms. The island's year-round warm climate and reliable waves created a European surf destination that rivals tropical locations, hosting numerous international competitions. Local Canarian culture blends Spanish traditions with African influences, creating a unique island vibe that welcomes international surf travelers. The surf industry now drives much of Fuerteventura's economy, with surf schools, camps, and board shapers establishing a thriving wave-riding community. Trade winds and consistent North Atlantic swells make the island a reliable training ground for European surfers seeking to hone their skills in powerful, consistent waves."
    ),
    Spot(
        id = "pastapoint",
        imageRes = R.drawable.pastapoint_spot_picture,
        spotName = "Pasta Point",
        country = "Maldives",
        imageContentDescription = "Pasta Point spot picture",
        difficultyLevel = 3,
        peakSeasonStart = "04-01",
        peakSeasonEnd = "05-31",
        gpsCoordinates = "3° 04′ 00″ N, 73° 09′ 00″ E",
        surfingCulture = "Pasta Point represents the pinnacle of tropical surfing luxury, where crystal-clear waters break over shallow coral reefs in perfect barrels. The wave was \"discovered\" by surf tourists in the 1990s, though local fishermen had observed these breaks for centuries while navigating between atolls. Maldivian surf culture centers around exclusive surf resorts and boat trips, creating a high-end surf tourism model that protects waves through limited access. The break's perfection comes from deep ocean swells wrapping around coral atolls, creating mechanical waves that seem almost too perfect to be natural. Climate change and coral bleaching now threaten these pristine surf spots, making each session precious and highlighting surfing's relationship with environmental conservation."
    ),
    Spot(
        id = "jeffreysbay",
        imageRes = R.drawable.jeffreysbay_spot_picture,
        spotName = "Jeffreys Bay",
        country = "South Africa",
        imageContentDescription = "Supertubes spot picture",
        difficultyLevel = 5,
        peakSeasonStart = "08-01",
        peakSeasonEnd = "10-09",
        gpsCoordinates = "34° 03′ 00″ S, 24° 55′ 00″ E",
        surfingCulture = "J-Bay established itself as Africa's premier surf destination in the 1960s, when surfers discovered the perfect right-hand point break during apartheid-era South Africa. The wave became internationally famous through surf films and the annual Billabong Pro competition, showcasing South African surfing talent to the world. Local surf culture reflects the country's complex history, with post-apartheid integration creating a more diverse and inclusive surfing community. The town of Jeffreys Bay transformed from a small fishing village into a global surf industry hub, hosting major surfboard manufacturers and surf brands. Great white sharks patrol these waters, adding an element of danger that has shaped the fearless character of South African surfing culture."
    ),
    Spot(
        id = "kittyhawk",
        imageRes = R.drawable.kittyhawk_spot_picture,
        spotName = "Kitty Hawk, North Carolina",
        country = "USA",
        imageContentDescription = "Kitty Hawk spot picture",
        difficultyLevel = 3,
        peakSeasonStart = "08-09",
        peakSeasonEnd = "10-18",
        gpsCoordinates = "36° 04′ 00″ N, 75° 42′ 00″ W",
        surfingCulture = "The Outer Banks surf scene developed alongside America's East Coast surf culture in the 1960s, with consistent Atlantic hurricane swells creating world-class waves. This barrier island chain holds historical significance as the birthplace of aviation, where the Wright brothers first flew, adding pioneering spirit to the local surf culture. Hurricane season brings powerful swells that can rival any surf destination globally, creating a hardcore community of surfers willing to brave dangerous conditions. The area's relative isolation and harsh winter conditions have fostered a tight-knit surf community that values authenticity over commercialization. Local surfers developed unique techniques for reading rapidly changing sandbars and powerful shore break, creating a distinct East Coast surfing style."
    ),
    Spot(
        id = "rockaway",
        imageRes = R.drawable.rockaway_spot_picture,
        spotName = "Rockaway Beach, Oregon",
        country = "USA",
        imageContentDescription = "Rockaway Beach spot picture",
        difficultyLevel = 1,
        peakSeasonStart = "08-23",
        peakSeasonEnd = "10-17",
        gpsCoordinates = "45° 37′ 00″ N, 123° 57′ 00″ W",
        surfingCulture = "Rockaway Beach represents the rugged character of Pacific Northwest surfing, where cold water and powerful waves demand respect and proper equipment. The surf culture here emerged in the 1960s despite frigid water temperatures requiring full wetsuits year-round, attracting dedicated surfers seeking uncrowded waves. Oregon's environmental consciousness influences the local surf community, with strong emphasis on beach cleanups and ocean conservation efforts. The consistent but challenging conditions create skilled surfers who can handle powerful waves in adverse weather conditions. Rockaway's surf culture embodies the Pacific Northwest's independent spirit, where surfers value solitude and connection with raw nature over tropical perfection."
    )
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

    val filteredSpots = allSpots.filter {
        it.country.contains(query, ignoreCase = true)
    }

    val context = LocalContext.current

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
                onQueryChange = { query = it })
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
