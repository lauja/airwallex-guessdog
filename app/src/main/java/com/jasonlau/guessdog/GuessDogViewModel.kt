package com.jasonlau.guessdog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuessDogViewModel @Inject constructor(
    private val guessDogRepository: GuessDogRepository
) : ViewModel() {
    fun getAllBreeds() {
        viewModelScope.launch {
            val allBreeds = guessDogRepository.getAllBreeds()
        }
    }
}