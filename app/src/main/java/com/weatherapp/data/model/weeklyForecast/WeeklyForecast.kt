package com.weatherapp.data.model.weeklyForecast


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeeklyForecast(
    @SerializedName("daily")
    var daily: List<Daily>?
) : Serializable