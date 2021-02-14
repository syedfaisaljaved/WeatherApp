package com.weatherapp.data.model.weather


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Wind(
    @SerializedName("deg")
    var deg: Double,
    @SerializedName("speed")
    var speed: Double
) : Serializable