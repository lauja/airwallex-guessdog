package com.jasonlau.guessdog.data

data class BreedData(
    val imageUrl: String,
    val breedAnswers: List<String>,
    val correctAnswer: String,
)
