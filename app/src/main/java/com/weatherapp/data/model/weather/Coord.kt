package com.weatherapp.data.model.weather


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coord(
    @SerializedName("lat")
    var lat: Double,
    @SerializedName("lon")
    var lon: Double
) : Serializable