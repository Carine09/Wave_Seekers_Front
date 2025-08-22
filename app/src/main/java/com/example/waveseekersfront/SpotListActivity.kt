package com.example.waveseekersfront

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.waveseekersfront.ui.theme.WaveSeekersFrontTheme
import androidx.compose.material3.Text

class SpotListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaveSeekersFrontTheme {
                Text(text = "Bienvenue dans SpotListActivity !")
            }
        }
    }
}

