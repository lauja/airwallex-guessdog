package com.jasonlau.guessdog.util

class FakeRandomBreedChooserImpl: RandomBreedChooser {
    override fun chooseRandomBreedLabels(
        numberOfNamesToChoose: Int,
        correctAnswerKey: String,
        breedMap: Map<String, String>
    ): List<String> {
        return listOf(
            "Kombai",
            "Komondor",
            "Kuvasz",
            "Labradoodle",
        )
    }
}