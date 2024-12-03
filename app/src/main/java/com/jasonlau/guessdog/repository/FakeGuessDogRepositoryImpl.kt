package com.jasonlau.guessdog.repository

import com.jasonlau.guessdog.data.AllBreedsResponse
import com.jasonlau.guessdog.data.RandomBreedImageResponse
import com.jasonlau.guessdog.data.Status

class FakeGuessDogRepositoryImpl: GuessDogRepository {
    override suspend fun getAllBreeds(): AllBreedsResponse = AllBreedsResponse(
        breeds = mapOf(
            "appenzeller" to emptyArray(),
            "australian" to arrayOf("kelpie", "shepherd"),
            "bakharwal" to arrayOf("indian")
        ),
        status = Status.SUCCESS
    )

    override suspend fun getRandomBreedImage(): RandomBreedImageResponse =
        RandomBreedImageResponse(imageUrl = "https://imageUrl", status = Status.SUCCESS)
}