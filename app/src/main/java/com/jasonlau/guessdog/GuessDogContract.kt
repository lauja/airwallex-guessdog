package com.jasonlau.guessdog

import com.jasonlau.guessdog.data.BreedData

data class GuessDogUiState(
    val isLoading: Boolean = false,
    val data: BreedData? = null,
    val numberCorrect: Int = 0,
    val numberAnswered: Int = 0,
    val hasError: Boolean = false,
    val gameOver: Boolean = false,
)
