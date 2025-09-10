package com.example.waveseekersfront

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.waveseekersfront.ui.theme.NeueMontrealMediumFontFamily
import com.example.waveseekersfront.ui.theme.NeueMontrealRegularFontFamily
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme

class NewSpotActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WaveSeekersFrontTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DisplayNewSpotForm(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

data class NewApiSpot(
    val destination: String,
    val location: String,
    val lat: Double,
    val long: Double,
    val peakSeasonStart: String,
    val peakSeasonEnd: String,
    val difficultyLevel: Int,
    val surfingCulture: String,
    val imageUrl: String
)

@Composable
fun AddSpotHeaderSection(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "New Spot",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        )
        Image(
            painter = painterResource(R.drawable.wave_seekers_secondary_logo_light),
            contentDescription = "Secondary wave seekers logo",
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DifficultyMenuDropDown(
    selectedDifficulty: String,
    onDifficultySelected: (String) -> Unit
) {
    val list = listOf(
        "1 - Beginner - Gentle waves, safe conditions, perfect for learning",
        "2 - Novice - Small to medium waves, mostly forgiving breaks",
        "3 - Intermediate - Consistent waves, requires solid skills",
        "4 - Advanced - Powerful waves, extensive experience needed",
        "5 - Expert Only - Massive dangerous waves, serious consequences"
    )
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            TextField(
                modifier = Modifier.menuAnchor(),
                value = if (selectedDifficulty.isEmpty()) "Select difficulty" else selectedDifficulty,
                onValueChange = {},
                readOnly = true,
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedContainerColor = MaterialTheme.colorScheme.onTertiary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
                    focusedTextColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary
                ),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) }
            )
            ExposedDropdownMenu(
                modifier = Modifier.background(MaterialTheme.colorScheme.onSecondary),
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                list.forEach { text ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = text,
                                style = TextStyle(
                                    fontFamily = NeueMontrealRegularFontFamily,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        },
                        onClick = {
                            onDifficultySelected(text)
                            isExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

@Composable
fun AddSpotContent() {
    var spotName by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var gpsCoordinates by remember { mutableStateOf("") }
    var startSeason by remember { mutableStateOf("") }
    var endSeason by remember { mutableStateOf("") }
    var waveDifficulty by remember { mutableStateOf("") }
    var surfingCulture by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    )

    val context = LocalContext.current

    Column {
        // Spot name
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.spot_name_blue_icon),
                contentDescription = "Blue wave icon",
                modifier = Modifier.height(40.dp)
            )
            Text(
                text = "Spot's name",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        OutlinedTextField(
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            value = spotName,
            onValueChange = { spotName = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
        )

        // Country
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.country_blue_icon),
                contentDescription = "Blue world icon",
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "Country",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        OutlinedTextField(
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            value = country,
            onValueChange = { country = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp)
        )

        // GPS coordinates
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.gps_coordinates_blue_icon),
                contentDescription = "Blue location pin icon",
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "GPS coordinates (lat, long => ex: 9.4444, -7.345)",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        OutlinedTextField(
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary
            ),
            value = gpsCoordinates,
            onValueChange = { gpsCoordinates = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp)
        )

        // Peak season start
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.peak_season_blue_icon),
                contentDescription = "Blue calendar icon",
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "Peak surf season starts (MM/DD)",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        OutlinedTextField(
            value = startSeason,
            onValueChange = { startSeason = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            )
        )

        // Peak season end
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.peak_season_blue_icon),
                contentDescription = "Blue calendar icon",
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "Peak surf season ends (MM/DD)",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        OutlinedTextField(
            value = endSeason,
            onValueChange = { endSeason = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            )
        )

        // Difficulty level
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.difficulty_star_darkblue_full_icon),
                contentDescription = "Blue star icon",
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "Difficulty level",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        DifficultyMenuDropDown(
            selectedDifficulty = waveDifficulty,
            onDifficultySelected = { waveDifficulty = it }
        )

        // Surfing culture
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.surfing_culture_blue_icon),
                contentDescription = "Blue information icon",
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "Tell us more about the surfing culture there",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        OutlinedTextField(
            value = surfingCulture,
            onValueChange = { surfingCulture = it },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp),
            colors = TextFieldDefaults.colors(
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            )
        )

        // Image picker
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.add_image_blue_icon),
                contentDescription = "Blue picture icon",
                modifier = Modifier.height(30.dp)
            )
            Text(
                text = "Spot's picture",
                fontFamily = NeueMontrealMediumFontFamily,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
        Column {
            Button(
                onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier.padding(top = 10.dp).fillMaxWidth(),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text(
                    text = "Add from gallery",
                    fontFamily = NeueMontrealMediumFontFamily,
                    color = MaterialTheme.colorScheme.surface
                )
            }

            if (selectedImageUri != null) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                )
            }
        }

        // Submit button
        SubmitButton(
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        ) {
            val latLong = gpsCoordinates.split(",")
            val lat = latLong.getOrNull(0)?.toDoubleOrNull() ?: 0.0
            val long = latLong.getOrNull(1)?.toDoubleOrNull() ?: 0.0
            val difficulty = waveDifficulty.filter { it.isDigit() }.toIntOrNull() ?: 1

            val spot = NewApiSpot(
                destination = spotName,
                location = country,
                lat = lat,
                long = long,
                peakSeasonStart = startSeason,
                peakSeasonEnd = endSeason,
                difficultyLevel = difficulty,
                surfingCulture = surfingCulture,
                imageUrl = imageUrl
            )

            Thread {
                val response = ApiService.addSpot(spot)
                Log.d("ADD_SPOT", "Response: $response")
            }.start()
        }
    }
}

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
    ) {
        Text(
            "Submit",
            fontFamily = NeueMontrealMediumFontFamily,
            color = MaterialTheme.colorScheme.surface,
        )
    }
}

@Composable
fun NavBarButtonAddSpot(
    iconRes: Int,
    text: String,
    iconContentDescription: String,
    isActive: Boolean = false,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(vertical = 4.dp).clickable { onClick() }
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
fun BottomNavBarAddSpot(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth().background(MaterialTheme.colorScheme.onSecondary).padding(horizontal = 24.dp)
    ) {
        NavBarButtonAddSpot(
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
        NavBarButtonAddSpot(
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
        NavBarButtonAddSpot(
            iconRes = R.drawable.add_spot_blue_icon,
            text = "Add a spot",
            iconContentDescription = "Add a spot icon",
            isActive = true,
            onClick = {
                val intent = Intent(context, NewSpotActivity::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DisplayNewSpotForm(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(24.dp)
        ) {
            AddSpotHeaderSection()
            AddSpotContent()
        }
        BottomNavBarAddSpot()
    }
}

@Preview(showBackground = true)
@Composable
fun AddSpotHeaderPreview() {
    WaveSeekersFrontTheme {
        AddSpotHeaderSection(modifier = Modifier.padding(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AddSpotNavBarPreview() {
    WaveSeekersFrontTheme {
        BottomNavBarAddSpot()
    }
}

@Preview(showBackground = true)
@Composable
fun NewSpotPreview() {
    WaveSeekersFrontTheme {
        DisplayNewSpotForm()
    }
}

@Preview(showBackground = true)
@Composable
fun AddSpotContentPreview() {
    WaveSeekersFrontTheme {
        AddSpotContent()
    }
}
