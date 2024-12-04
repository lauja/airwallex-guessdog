package com.jasonlau.guessdog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
) {
    var isAnswerCorrect by remember { mutableStateOf<Boolean?>(null) }
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
            if (isAnswerCorrect != null) {
                Text(
                    if (isAnswerCorrect == true) {
                        "Correct"
                    } else {
                        "Incorrect"
                    }
                )
            }
            possibleChoices.forEach {
                Button(
                    onClick = {
                        if (correctAnswer === it) {
                            isAnswerCorrect = true
                        } else {
                            isAnswerCorrect = false
                        }
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
            correctAnswer = "A"
        )
    }
}

