package com.jasonlau.guessdog.data

import com.google.gson.annotations.SerializedName

data class AllBreedsResponse(
    @SerializedName("message")
    val breeds: Map<String, Array<String>>,
    val status: Status,
)
