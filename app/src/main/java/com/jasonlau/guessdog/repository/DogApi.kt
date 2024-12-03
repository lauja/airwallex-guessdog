package com.jasonlau.guessdog.repository

import com.jasonlau.guessdog.data.AllBreedsResponse
import com.jasonlau.guessdog.data.RandomBreedImageResponse
import retrofit2.http.GET

interface DogApi {
    @GET("breeds/list/all")
    suspend fun getAllBreeds() : AllBreedsResponse

    @GET("breeds/image/random")
    suspend fun getRandomBreedImage() : RandomBreedImageResponse
}