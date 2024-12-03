package com.jasonlau.guessdog.repository

import com.jasonlau.guessdog.data.AllBreedsResponse
import com.jasonlau.guessdog.data.RandomBreedImageResponse

interface GuessDogRepository {
    suspend fun getAllBreeds(): AllBreedsResponse
    suspend fun getRandomBreedImage(): RandomBreedImageResponse
}
