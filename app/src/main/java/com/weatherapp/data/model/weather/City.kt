package com.weatherapp.data.model.weather


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class City(
        @SerializedName("base")
    var base: String? = null,
        @SerializedName("clouds")
    var clouds: Clouds? = null,
        @SerializedName("cod")
    var cod: Double? = null,
        @SerializedName("coord")
    var coord: Coord? = null,
        @SerializedName("dt")
    var dt: Double? = null,
        @SerializedName("id")
    var id: Double? = null,
        @SerializedName("main")
    var main: Main? = null,
        @SerializedName("name")
    var name: String? = null,
        @SerializedName("sys")
    var sys: Sys? = null,
        @SerializedName("timezone")
    var timezone: Double? = null,
        @SerializedName("visibility")
    var visibility: Double? = null,
        @SerializedName("weather")
    var weather: List<Weather>,
        @SerializedName("wind")
    var wind: Wind
) : Serializable