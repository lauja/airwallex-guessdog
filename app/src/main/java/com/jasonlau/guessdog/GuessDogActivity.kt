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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                GuessDogScaffold(
                    onRestartGame = { viewModel.getDogData() }
                ) {
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessDogScaffold(
    onRestartGame: () -> Unit,
    content: @Composable() () -> Unit,
) {
    var showResetAlertDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.toolbar_title),
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    actions = {
                        TextButton(
                            onClick = { showResetAlertDialog = true }
                        ) {
                            Text(text = stringResource(R.string.reset_button_label))
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            content()
        }
    }

    if (showResetAlertDialog) {
        ResetAlertDialog(
            onDismiss = {
                showResetAlertDialog = false
            },
            onConfirm = {
                showResetAlertDialog = false
                onRestartGame()
            }
        )
    }
}

@Composable
private fun ResetAlertDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = stringResource(R.string.reset_dialog_ok_button_label))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = stringResource(R.string.reset_dialog_cancel_button_label))
            }
        },
        title = { Text(text = stringResource(R.string.reset_dialog_title)) },
        text = { Text(text = stringResource(R.string.reset_dialog_description)) },
    )
}


@Composable
fun ErrorPage(onTryAgain: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Warning,
                contentDescription = "warning",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.error_page_description), textAlign = TextAlign.Center)
            TextButton(onClick = onTryAgain) {
                Text(text = stringResource(R.string.error_page_button_label))
            }
        }
    }

}

@Preview
@Composable
fun PreviewResetAlertDialog() {
    GuessDogTheme {
        ResetAlertDialog({}, {})
    }
}

@Preview
@Composable
fun PreviewGuessDogScaffold() {
    GuessDogTheme {
        GuessDogScaffold(
            onRestartGame = { }
        ) { }
    }
}

@Composable
@Preview
fun PreviewErrorPage() {
    GuessDogTheme {
        Surface {
            ErrorPage { }
        }
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
