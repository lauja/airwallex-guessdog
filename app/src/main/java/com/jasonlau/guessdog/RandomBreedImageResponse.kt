package com.jasonlau.guessdog

import com.google.gson.annotations.SerializedName

data class RandomBreedImageResponse(
    @SerializedName("message")
    val imageUrl: String,
    val status: Status,
)