package com.jasonlau.guessdog.repository

import com.jasonlau.guessdog.data.AllBreedsResponse
import com.jasonlau.guessdog.data.RandomBreedImageResponse
import com.jasonlau.guessdog.data.Status
import retrofit2.Response

class FakeGuessDogRepositoryImpl: GuessDogRepository {
    override suspend fun getAllBreeds(): Response<AllBreedsResponse> = Response.success(
        AllBreedsResponse(
            breeds = mapOf(
                "keeshond" to emptyArray(),
                "kelpie" to emptyArray(),
                "kombai" to emptyArray(),
                "komondor" to emptyArray(),
                "kuvasz" to emptyArray(),
                "labradoodle" to emptyArray(),
                "labrador" to emptyArray(),
                "leonberg" to emptyArray(),
                "lhasa" to emptyArray(),
                "malamute" to emptyArray(),
                "malinois" to emptyArray(),
            ),
            status = Status.SUCCESS
        )
    )

    override suspend fun getRandomBreedImage(): Response<RandomBreedImageResponse> = Response.success(
        RandomBreedImageResponse(
            imageUrl = "https://images.dog.ceo/breeds/labradoodle/n02112706_62.jpg",
            status = Status.SUCCESS
        )
    )
}