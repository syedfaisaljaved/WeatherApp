package com.weatherapp.data.model.weather


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Main(
    @SerializedName("feels_like")
    var feelsLike: Double,
    @SerializedName("humidity")
    var humidity: Double,
    @SerializedName("pressure")
    var pressure: Double,
    @SerializedName("temp")
    var temp: Double,
    @SerializedName("temp_max")
    var tempMax: Double,
    @SerializedName("temp_min")
    var tempMin: Double
) : Serializable