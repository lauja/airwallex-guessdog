package com.jasonlau.guessdog.util

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test

class RandomBreedChooserTest {
    @Test
    fun `chooseRandomBreedLabels should choose the required number of breeds`() {
        val randomBreedChooser = RandomBreedChooserImpl()
        val breedMap =
            mapOf(
                "keeshond" to "Keeshond",
                "kelpie" to "Kelpie",
                "kombai" to "Kombai",
                "komondor" to "Komondor",
                "kuvasz" to "Kuvasz",
                "labradoodle" to "Labradoodle",
                "labrador" to "Labrador",
                "leonberg" to "Leonberg",
                "lhasa" to "Lhasa",
                "malamute" to "Malamute",
                "malinois" to "Malinois",
            )
        val randomBreedLabels = randomBreedChooser.chooseRandomBreedLabels(
            numberOfNamesToChoose = 3,
            correctAnswerKey = "kombai",
            breedMap = breedMap
        )

        assertThat(randomBreedLabels.size, equalTo(4))
        assertTrue(breedMap.values.containsAll(randomBreedLabels))
        assertThat(randomBreedLabels, hasItem("Kombai"))
    }
}