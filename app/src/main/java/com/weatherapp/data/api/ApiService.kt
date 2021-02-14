package com.weatherapp.data.api

import com.weatherapp.data.model.weather.City
import com.weatherapp.data.model.weeklyForecast.WeeklyForecast
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("weather")
    fun getCurrentWeather(
            @Query("lat") latitude: Double?,
            @Query("lon") longitude: Double?,
            @Query("units") unit: String,
            @Query("appid") apiKey: String
    ): Call<City>

    @GET("weather")
    fun getDateWiseWeather(
        @Query("id") cityId: String?,
        @Query("units") unit: String,
        @Query("appid") apiKey: String
    ): Call<City>

    @GET("onecall")
    fun getWeeklyWeather(
            @Query("lat") latitude: Double?,
            @Query("lon") longitude: Double?,
            @Query("appid") apiKey: String,
            @Query("exclude") exclude: String,
            @Query("units") unit: String
    ): Call<WeeklyForecast>
}