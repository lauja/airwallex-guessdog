package com.jasonlau.guessdog.repository

import com.jasonlau.guessdog.data.AllBreedsResponse
import com.jasonlau.guessdog.data.RandomBreedImageResponse
import retrofit2.Response

class GuessDogRepositoryImpl(
    private val dogApi: DogApi
): GuessDogRepository {
    override suspend fun getAllBreeds(): Response<AllBreedsResponse> = dogApi.getAllBreeds()

    override suspend fun getRandomBreedImage(): Response<RandomBreedImageResponse> = dogApi.getRandomBreedImage()
}