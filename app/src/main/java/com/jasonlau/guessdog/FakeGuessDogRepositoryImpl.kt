package com.jasonlau.guessdog

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