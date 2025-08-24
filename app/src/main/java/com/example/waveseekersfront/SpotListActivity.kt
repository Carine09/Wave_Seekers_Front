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
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.waveseekersfront.ui.theme.NeueMontrealBoldFontFamily
import com.example.waveseekersfront.ui.theme.NeueMontrealMediumFontFamily
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme

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
fun HeaderSection(modifier: Modifier = Modifier) {
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
fun SpotCard(
    imageRes: Int,
    spotName: String,
    country: String,
    imageContentDescription: String,
    difficultyLevel: Int = 1,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
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
fun SpotListContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        SpotCard(
            imageRes = R.drawable.oahu_spot_picture,
            spotName = "Oahu, Hawaii",
            country = "United States of America",
            imageContentDescription = "Oahu spot picture",
            difficultyLevel = 4
        )
        SpotCard(
            imageRes = R.drawable.skeletonbay_spot_picture,
            spotName = "Skeleton Bay",
            country = "Namibia",
            imageContentDescription = "Skeleton Bay spot picture",
            difficultyLevel = 5
        )
        SpotCard(
            imageRes = R.drawable.superbank_spot_picture,
            spotName = "Superbank, Gold Coast",
            country = "Australia",
            imageContentDescription = "Superbank spot picture",
            difficultyLevel = 4
        )
    }
}

@Composable
fun NavBarButton(
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
fun BottomNavBar(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(horizontal = 24.dp)
    ) {
        NavBarButton(
            iconRes = R.drawable.logout_grey_icon,
            text = "Log out",
            iconContentDescription = "Log out icon",
            isActive = false,
            onClick = {
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.weight(1f)
        )
        NavBarButton(
            iconRes = R.drawable.home_blue_icon,
            text = "Home",
            iconContentDescription = "Home icon",
            isActive = true,
            onClick = {
                // Already on home page, no action needed
            },
            modifier = Modifier.weight(1f)
        )
        NavBarButton(
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
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            HeaderSection()
            SpotListContent()
        }
        BottomNavBar()
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    WaveSeekersFrontTheme {
        HeaderSection(modifier = Modifier.padding(32.dp))
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
        SpotListContent(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavBarPreview() {
    WaveSeekersFrontTheme {
        BottomNavBar(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SpotListPreview() {
    WaveSeekersFrontTheme {
        DisplaySpotList()
    }
}