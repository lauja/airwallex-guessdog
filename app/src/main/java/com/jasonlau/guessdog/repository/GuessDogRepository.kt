package com.jasonlau.guessdog.repository

import com.jasonlau.guessdog.data.AllBreedsResponse
import com.jasonlau.guessdog.data.RandomBreedImageResponse
import retrofit2.Response

interface GuessDogRepository {
    suspend fun getAllBreeds(): Response<AllBreedsResponse>
    suspend fun getRandomBreedImage(): Response<RandomBreedImageResponse>
}
