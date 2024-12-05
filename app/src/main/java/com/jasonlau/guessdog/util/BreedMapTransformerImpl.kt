package com.jasonlau.guessdog.util

import java.util.Locale

class BreedMapTransformerImpl: BreedMapTransformer {
    /**
     * Transforms the response from all breeds to a key-value
     * map of breed to display label
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
    override fun transformBreedsResponseToDisplayMap(allBreedsMap: Map<String, Array<String>>): Map<String, String> {
        val keyToDisplayMap = mutableMapOf<String, String>()

        allBreedsMap.entries.forEach { entry ->
            val breed = entry.key
            if (entry.value.isEmpty()) {
                keyToDisplayMap[breed] = breed.capitalise()
            } else {
                entry.value.forEach { subBreed ->
                    keyToDisplayMap["$breed-$subBreed"] = "${subBreed.capitalise()} ${breed.capitalise()}"
                }
            }
        }

        return keyToDisplayMap
    }

    private fun String.capitalise(): String =
        this.replaceFirstChar { firstChar -> if (firstChar.isLowerCase()) firstChar.titlecase(Locale.getDefault()) else firstChar.toString() }
}