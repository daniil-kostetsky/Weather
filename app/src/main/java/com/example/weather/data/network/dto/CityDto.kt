package com.example.weather.data.network.dto

import kotlinx.serialization.SerialName

data class CityDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("country")
    val country: String,
)