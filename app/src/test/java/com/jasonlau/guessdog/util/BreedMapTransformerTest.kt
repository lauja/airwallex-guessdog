package com.jasonlau.guessdog.util

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class BreedMapTransformerTest {
    /*
     * eg.
     * 		"appenzeller": [],
     * 		"australian": ["kelpie", "shepherd"],
     * 		"bakharwal": ["indian"],
     *
     *      "appenzeller": "Appenzeller",
     *      "australian-kelpie": "Kelpie Australian"
     *      "australian-shepherd": "Shepherd Australian"
     *      "bakharwal-indian": "Indian Bakharwal"
     */
    @Test
    fun `transformBreedsResponseToDisplayMap should add sub breeds to map`() {
        val breedMapTransformer = BreedMapTransformerImpl()
        val result = breedMapTransformer.transformBreedsResponseToDisplayMap(
            mapOf(
                "appenzeller" to emptyArray(),
                "australian" to arrayOf("kelpie", "shepherd"),
                "bakharwal" to arrayOf("indian")
            )
        )

        assertThat(
            result, equalTo(
                mapOf(
                    "appenzeller" to "Appenzeller",
                    "australian-kelpie" to "Kelpie Australian",
                    "australian-shepherd" to "Shepherd Australian",
                    "bakharwal-indian" to "Indian Bakharwal",
                )
            )
        )
    }
}