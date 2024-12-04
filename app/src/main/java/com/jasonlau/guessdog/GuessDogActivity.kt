package com.jasonlau.guessdog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jasonlau.guessdog.ui.GuessDogScreen
import com.jasonlau.guessdog.ui.theme.GuessDogTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuessDogActivity : ComponentActivity() {
    private val viewModel: GuessDogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val viewState by viewModel.viewState.collectAsStateWithLifecycle()
            viewModel.getDogData()
            GuessDogTheme {
                viewState.contentStateOrNull?.data?.let {
                    GuessDogScreen(
                        imageUrl = it.imageUrl,
                        possibleChoices = it.breedAnswers,
                        correctAnswer = it.correctAnswer,
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GuessDogTheme {
        Greeting("Android")
    }
}