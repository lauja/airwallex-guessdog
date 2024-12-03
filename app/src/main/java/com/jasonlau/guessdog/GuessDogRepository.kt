package com.jasonlau.guessdog

class GuessDogRepository(
    private val dogApi: DogApi
) {
    suspend fun getAllBreeds(): AllBreedsResponse {
        return dogApi.getAllBreeds()
    }
}