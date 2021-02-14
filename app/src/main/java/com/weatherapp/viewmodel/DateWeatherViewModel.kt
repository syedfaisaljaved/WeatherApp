package com.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.data.model.weather.City
import com.weatherapp.repo.WeatherRepo

class DateWeatherViewModel : ViewModel(){

    private val weatherRepo = WeatherRepo.instance!!

    fun getDateWiseWeatherResponse(): LiveData<City> = weatherRepo.dateWiseWeatherResponse

    fun getDateWiseWeather(cityId : String, unit: String) = weatherRepo.getDateWiseWeather(cityId,unit)

}