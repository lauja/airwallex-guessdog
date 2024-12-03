package com.jasonlau.guessdog.data

import com.google.gson.annotations.SerializedName

data class RandomBreedImageResponse(
    @SerializedName("message")
    val imageUrl: String,
    val status: Status,
)