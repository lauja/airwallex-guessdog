package com.jasonlau.guessdog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jasonlau.guessdog.ui.theme.GuessDogTheme

private const val NUMBER_OF_QUESTIONS = 10

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessDogScreen(
    imageUrl: String,
    possibleChoices: List<String>,
    correctAnswer: String,
    onAnswerSelected: (selected: String, correct: String) -> Unit,
    numberCorrect: Int,
    numberAnswered: Int,
    onRestartGame: () -> Unit,
    onNextClicked: () -> Unit,
    gameOver: Boolean
) {
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showResetAlertDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = "GuessDog",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                        )
                    },
                    actions = {
                        TextButton(
                            onClick = { showResetAlertDialog = true }
                        ) {
                            Text(text = "RESET")
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                placeholder = if (LocalInspectionMode.current) {
                    ColorPainter(Color.LightGray)
                } else {
                    null
                },
                modifier = Modifier
                    .weight(1f)
                    .align(alignment = Alignment.CenterHorizontally)
            )

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Score: $numberCorrect/$numberAnswered",
                        )
                        EmojiIndicator(numberAnswered, numberCorrect)
                    }


                    TextButton(
                        onClick = {
                            onNextClicked()
                            selectedAnswer = null
                        },
                        enabled = selectedAnswer != null,
                    ) {
                        Text(text = "NEXT >")
                    }
                }
                possibleChoices.forEach {
                    Button(
                        onClick = {
                            if (selectedAnswer == null) {
                                onAnswerSelected(it, correctAnswer)
                                selectedAnswer = it
                            }
                        },
                        colors = if (selectedAnswer != null) {
                            when (it) {
                                correctAnswer -> {
                                    ButtonDefaults.buttonColors(containerColor = Color.Green)
                                }
                                selectedAnswer -> {
                                    ButtonDefaults.buttonColors(containerColor = Color.Red)
                                }
                                else -> {
                                    ButtonDefaults.buttonColors()
                                }
                            }
                        } else {
                            ButtonDefaults.buttonColors()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = it)
                    }
                }
            }
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
                selectedAnswer = null
            }
        )
    } else if (gameOver) {
        GameOverAlertDialog(
            onConfirm = {
                onRestartGame()
            },
            score = numberCorrect,
        )
    }
}

@Composable
private fun EmojiIndicator(numberAnswered: Int, numberCorrect: Int) {
    val emojiCold = "\uD83E\uDD76"
    val emojiHot = "\uD83D\uDD25"
    if (numberAnswered > 5) {
        val ratioCorrect = numberCorrect.toDouble() / numberAnswered.toDouble()
        val indicatorText = when {
            ratioCorrect < 0.5 -> emojiCold
            ratioCorrect > 0.8 -> emojiHot
            else -> null
        }

        indicatorText?.let {
            Text(
                text = it,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
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
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "CANCEL")
            }
        },
        title = { Text(text = "Warning") },
        text = { Text(text = "Are you sure you wish to reset this game? All progress will be lost!") },
    )
}

@Composable
private fun GameOverAlertDialog(
    onConfirm: () -> Unit,
    score: Int,
) {
    val perfectScore = score == NUMBER_OF_QUESTIONS
    val title = if (perfectScore) {
        "Congratulations!"
    } else {
        "Better luck next time!"
    }

    val text = if (perfectScore) {
        "You are already a dog expert!"
    } else {
        "Keep playing to try to improve your score"
    }

    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = "PLAY AGAIN")
            }
        },
        title = { Text(text = title) },
        text = { Text(text = text) },
    )
}

@Preview
@Composable
fun PreviewGuessDogScreen() {
    GuessDogTheme {
        GuessDogScreen(
            imageUrl = "https://example.com/image.jpg",
            possibleChoices = listOf("Doberman", "Indian Rajapalayam", "Dingo", "Brabancon"),
            correctAnswer = "Doberman",
            onAnswerSelected = { _,_ -> },
            numberCorrect = 6,
            numberAnswered = 6,
            onRestartGame = { },
            onNextClicked = { },
            gameOver = false,
        )
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
fun PreviewGameOverAlertDialogExpert() {
    GuessDogTheme {
        GameOverAlertDialog({}, NUMBER_OF_QUESTIONS)
    }
}

@Preview
@Composable
fun PreviewGameOverAlertDialogNovice() {
    GuessDogTheme {
        GameOverAlertDialog({}, 5)
    }
}