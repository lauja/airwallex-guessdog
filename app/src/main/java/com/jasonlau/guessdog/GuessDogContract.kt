package com.jasonlau.guessdog

import com.jasonlau.guessdog.data.BreedData

interface GuessDogContract {
    sealed interface ViewState {
        data class Content(
            val data: BreedData,
            val numberCorrect: Int = 0,
            val numberAnswered: Int = 0,
        ): ViewState
        data object Error : ViewState
        data object Loading : ViewState

        val contentStateOrNull: Content?
            get() = (this as? Content)
    }
}
