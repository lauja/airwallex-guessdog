package com.jasonlau.guessdog.util

class FakeBreedMapTransformerImpl: BreedMapTransformer {
    override fun transformBreedsResponseToDisplayMap(allBreedsMap: Map<String, Array<String>>): Map<String, String> =
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
}