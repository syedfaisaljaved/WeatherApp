package com.weatherapp.data.model.weeklyForecast


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FeelsLike(
    @SerializedName("day")
    var day: Double?,
    @SerializedName("eve")
    var eve: Double?,
    @SerializedName("morn")
    var morn: Double?,
    @SerializedName("night")
    var night: Double?
) : Serializable