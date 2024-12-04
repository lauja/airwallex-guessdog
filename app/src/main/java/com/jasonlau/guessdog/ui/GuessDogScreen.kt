package com.jasonlau.guessdog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jasonlau.guessdog.ui.theme.GuessDogTheme

@Composable
fun GuessDogScreen(
    imageUrl: String,
    possibleChoices: List<String>,
    correctAnswer: String,
    onAnswerSelected: (selected: String, correct: String) -> Unit,
    numberCorrect: Int,
    numberAnswered: Int,
) {
    Column {
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
            var questionAnswered by remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Score: $numberCorrect/$numberAnswered",
                    modifier = Modifier.weight(1f)
                )

                TextButton(
                    onClick = { }
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
                        if (it === correctAnswer) {
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
        )
    }
}

