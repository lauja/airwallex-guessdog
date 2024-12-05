package com.jasonlau.guessdog.util

class RandomBreedChooserImpl: RandomBreedChooser {
    /**
     * Selects the number of required keys, then adds the correct answer key and shuffles the list
     * then returns the label for that given keys
     */
    override fun chooseRandomBreedLabels(numberOfNamesToChoose: Int, correctAnswerKey: String, breedMap: Map<String, String>): List<String> =
        breedMap.keys.filterNot { it == correctAnswerKey }
            .shuffled()
            .take(numberOfNamesToChoose)
            .plus(correctAnswerKey)
            .shuffled()
            .mapNotNull {
                breedMap[it]
            }
}