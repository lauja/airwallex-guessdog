package com.jasonlau.guessdog.repository

import com.jasonlau.guessdog.data.AllBreedsResponse
import com.jasonlau.guessdog.data.RandomBreedImageResponse

class GuessDogRepositoryImpl(
    private val dogApi: DogApi
): GuessDogRepository {
    override suspend fun getAllBreeds(): AllBreedsResponse = dogApi.getAllBreeds()

    override suspend fun getRandomBreedImage(): RandomBreedImageResponse = dogApi.getRandomBreedImage()
}