package com.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.data.model.weather.City
import com.weatherapp.repo.WeatherRepo


class CurrentWeatherViewModel : ViewModel(){

    private val weatherRepo = WeatherRepo.instance!!

    fun getCurrentWeatherResponse(): LiveData<City> = weatherRepo.currentWeatherResponse

    fun getCurrentWeather(latitude: Double?, longitude: Double?, unit: String) = weatherRepo.getCurrentWeather(latitude,longitude,unit)
}