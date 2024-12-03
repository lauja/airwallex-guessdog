package com.jasonlau.guessdog

import retrofit2.http.GET

interface DogApi {
    @GET("breeds/list/all")
    suspend fun getAllBreeds() : AllBreedsResponse
}