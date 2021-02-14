package com.weatherapp.ui.activity_base

import android.location.LocationListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.weatherapp.data.model.weeklyForecast.Daily
import com.weatherapp.data.sharedPrefs.UserSharedPrefs
import com.weatherapp.databinding.ActivitySelectedWeatherBinding
import com.weatherapp.util.CommonUtils.Companion.getDateTimeFromMillisecond
import com.weatherapp.util.CommonUtils.Companion.getWindSpeedUnit
import java.text.SimpleDateFormat
import java.util.*

class SelectedWeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectedWeatherBinding
    private val degree = "°"
    lateinit var userSharedPrefs: UserSharedPrefs
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedWeatherBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        userSharedPrefs = UserSharedPrefs.getSharedPref(this)
        getIncomingIntent()
    }

    private fun getIncomingIntent() {
        val weather = intent.extras?.get("weather_data") as Daily
        setWeather(weather)
    }

    private fun setWeather(cityWeather: Daily) {
        binding.username.text = "Hello, ${userSharedPrefs.getUsername()}"
        binding.weather.text = cityWeather.weather!![0].main
//        binding.weatherDesc.text = cityWeather.weather[0].description
        binding.minTemp.text = "Min. ${cityWeather.temp?.min}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.maxTemp.text = "Max. ${cityWeather.temp?.max}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.morningTemp.text = "${cityWeather.temp?.morn}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.dayTemp.text = "${cityWeather.temp?.day}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.eveTemp.text = "${cityWeather.temp?.eve}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.nightTemp.text = "${cityWeather.temp?.night}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.morningFeel.text = "${cityWeather.feelsLike?.morn}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.dayFeel.text = "${cityWeather.feelsLike?.day}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.eveFeel.text = "${cityWeather.feelsLike?.eve}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.nightFeel.text = "${cityWeather.feelsLike?.night}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.windSpeed.text = "${cityWeather.windSpeed} ${getWindSpeedUnit(userSharedPrefs)}"
        binding.humidity.text = "${cityWeather.humidity}%"
        binding.pressure.text = "${cityWeather.pressure} hPa"
        binding.cloudiness.text = "${cityWeather.clouds}%"
        binding.sunrise.text = getDateTimeFromMillisecond(cityWeather.sunrise?.toInt())
        binding.sunset.text = getDateTimeFromMillisecond(cityWeather.sunset?.toInt())

        Glide.with(this)
            .load("http://openweathermap.org/img/wn/${cityWeather.weather!![0].icon}@4x.png")
            .into(binding.weatherIcon)

    }

}