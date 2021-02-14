package com.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.weatherapp.data.model.weeklyForecast.WeeklyForecast
import com.weatherapp.repo.WeatherRepo

class WeeklyWeatherViewModel : ViewModel(){

	private val weatherRepo = WeatherRepo.instance!!

	fun getWeeklyWeatherResponse(): LiveData<WeeklyForecast> = weatherRepo.weeklyWeatherResponse

	fun getWeeklyWeather(latitude: Double?, longitude: Double?, unit: String) = weatherRepo.getWeeklyWeather(latitude,longitude,unit)


}