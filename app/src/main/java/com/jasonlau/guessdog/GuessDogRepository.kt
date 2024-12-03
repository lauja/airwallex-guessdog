package com.jasonlau.guessdog

interface GuessDogRepository {
    suspend fun getAllBreeds(): AllBreedsResponse
    suspend fun getRandomBreedImage(): RandomBreedImageResponse
}
