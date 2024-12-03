package com.jasonlau.guessdog

class GuessDogRepositoryImpl(
    private val dogApi: DogApi
): GuessDogRepository {
    override suspend fun getAllBreeds(): AllBreedsResponse = dogApi.getAllBreeds()

    override suspend fun getRandomBreedImage(): RandomBreedImageResponse = dogApi.getRandomBreedImage()
}