package com.jasonlau.guessdog.util

interface BreedMapTransformer {
    fun transformBreedsResponseToDisplayMap(allBreedsMap: Map<String, Array<String>>): Map<String, String>
}
