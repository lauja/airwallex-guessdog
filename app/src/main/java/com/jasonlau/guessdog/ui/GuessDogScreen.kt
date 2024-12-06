package com.jasonlau.guessdog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jasonlau.guessdog.R
import com.jasonlau.guessdog.ui.theme.GuessDogTheme

private const val NUMBER_OF_QUESTIONS = 10
private const val PASS_THRESHOLD = 0.5
private const val HIGH_SCORE_THRESHOLD = 0.8

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

    Column {
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
                        text = stringResource(
                            R.string.score_label,
                            numberCorrect,
                            numberAnswered
                        ),
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
                    Text(text = stringResource(R.string.next_button_label))
                }
            }
            possibleChoices.forEach {
                OutlinedButton(
                    onClick = {
                        if (selectedAnswer == null) {
                            onAnswerSelected(it, correctAnswer)
                            selectedAnswer = it
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = if (selectedAnswer != null) {
                        when (it) {
                            correctAnswer -> {
                                ButtonDefaults.outlinedButtonColors(containerColor = Color.Green)
                            }
                            selectedAnswer -> {
                                ButtonDefaults.outlinedButtonColors(containerColor = Color.Red)
                            }
                            else -> {
                                ButtonDefaults.outlinedButtonColors()
                            }
                        }
                    } else {
                        ButtonDefaults.outlinedButtonColors()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = it)
                }
            }
        }
    }

    if (gameOver) {
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
    if (numberAnswered > 5) {
        val ratioCorrect = numberCorrect.toDouble() / numberAnswered.toDouble()
        val indicatorText = when {
            ratioCorrect < PASS_THRESHOLD -> stringResource(R.string.emoji_cold_face)
            ratioCorrect > HIGH_SCORE_THRESHOLD -> stringResource(R.string.emoji_fire)
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
private fun GameOverAlertDialog(
    onConfirm: () -> Unit,
    score: Int,
) {
    val perfectScore = score == NUMBER_OF_QUESTIONS
    val title = if (perfectScore) {
        stringResource(R.string.game_over_dialog_perfect_title)
    } else {
        stringResource(R.string.game_over_dialog_imperfect_title)
    }

    val text = if (perfectScore) {
        stringResource(R.string.game_over_dialog_perfect_description)
    } else {
        stringResource(R.string.game_over_dialog_imperfect_description)
    }

    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(text = stringResource(R.string.game_over_dialog_button_label))
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
        Surface {
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