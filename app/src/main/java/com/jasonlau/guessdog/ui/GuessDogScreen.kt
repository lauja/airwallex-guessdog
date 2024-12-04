package com.jasonlau.guessdog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jasonlau.guessdog.ui.theme.GuessDogTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuessDogScreen(
    imageUrl: String,
    possibleChoices: List<String>,
    correctAnswer: String,
    onAnswerSelected: (selected: String, correct: String) -> Unit,
    numberCorrect: Int,
    numberAnswered: Int,
    onResetClicked: () -> Unit,
    onNextClicked: () -> Unit,
) {
    var questionAnswered by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 4.dp) {
                TopAppBar(
                    title = {
                        Text(
                            text = "GuessDog",
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    },
                    actions = {
                        TextButton(
                            onClick = {
                                onResetClicked()
                                questionAnswered = false
                            }
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
                modifier = Modifier.weight(1f)
            )

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Score: $numberCorrect/$numberAnswered",
                        modifier = Modifier.weight(1f)
                    )

                    TextButton(
                        onClick = {
                            onNextClicked()
                            questionAnswered = false
                        }
                    ) {
                        Text(text = "NEXT >")
                    }
                }
                possibleChoices.forEach {
                    Button(
                        onClick = {
                            if (!questionAnswered) {
                                onAnswerSelected(it, correctAnswer)
                                questionAnswered = true
                            }
                        },
                        colors = if (questionAnswered) {
                            if (it == correctAnswer) {
                                ButtonDefaults.buttonColors(containerColor = Color.Green)
                            } else {
                                ButtonDefaults.buttonColors(containerColor = Color.Red)
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
}

@Preview
@Composable
fun PreviewGuessDogScreen() {
    GuessDogTheme {
        GuessDogScreen(
            imageUrl = "https://example.com/image.jpg",
            possibleChoices = listOf("A", "B", "C", "D"),
            correctAnswer = "A",
            onAnswerSelected = { _,_ -> },
            numberCorrect = 0,
            numberAnswered = 0,
            onResetClicked = { },
            onNextClicked = { },
        )
    }
}

