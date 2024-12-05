package com.jasonlau.guessdog.util

interface RandomBreedChooser {
    fun chooseRandomBreedLabels(numberOfNamesToChoose: Int, correctAnswerKey: String, breedMap: Map<String, String>): List<String>
}
