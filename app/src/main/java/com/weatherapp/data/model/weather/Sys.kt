package com.weatherapp.data.model.weather


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Sys(
    @SerializedName("country")
    var country: String,
    @SerializedName("id")
    var id: Double,
    @SerializedName("sunrise")
    var sunrise: Double,
    @SerializedName("sunset")
    var sunset: Double,
    @SerializedName("type")
    var type: Double
) : Serializable