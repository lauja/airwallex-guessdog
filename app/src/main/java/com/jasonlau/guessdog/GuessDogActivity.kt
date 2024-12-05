package com.jasonlau.guessdog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                when {
                    viewState.hasError -> {
                        ErrorPage(
                            onTryAgain = { viewModel.getDogData(false) }
                        )
                    }
                    viewState.isLoading -> {
                        LoadingPage()
                    }
                    else -> {
                        viewState.data?.let {
                            GuessDogScreen(
                                imageUrl = it.imageUrl,
                                possibleChoices = it.breedAnswers,
                                correctAnswer = it.correctAnswer,
                                onAnswerSelected = viewModel::onAnswerSelected,
                                numberCorrect = viewState.numberCorrect,
                                numberAnswered = viewState.numberAnswered,
                                onRestartGame = { viewModel.getDogData() },
                                onNextClicked = { viewModel.getDogData(false) },
                                gameOver = viewState.gameOver,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorPage(onTryAgain: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Outlined.Warning,
                contentDescription = "warning",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "An unexpected error has occurred")
            TextButton(onClick = onTryAgain) {
                Text(text = "TRY AGAIN")
            }
        }
    }

}

@Composable
@Preview
fun PreviewErrorPage() {
    GuessDogTheme {
        ErrorPage { }
    }
}

@Composable
fun LoadingPage() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview
fun PreviewLoadingPage() {
    GuessDogTheme {
        LoadingPage()
    }
}
