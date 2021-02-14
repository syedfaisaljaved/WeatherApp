package com.weatherapp.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.weatherapp.data.api.ApiConstants.API_KEY
import com.weatherapp.data.api.RetrofitBuilder.apiService
import com.weatherapp.data.model.weather.City
import com.weatherapp.data.model.weeklyForecast.WeeklyForecast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "WeatherRepo"

class WeatherRepo {

    var currentWeatherResponse = MutableLiveData<City>()
    var dateWiseWeatherResponse = MutableLiveData<City>()
    var weeklyWeatherResponse = MutableLiveData<WeeklyForecast>()

    companion object{
        var instance: WeatherRepo? = null
            get() {
                if (field == null) {
                    field = WeatherRepo()
                }
                return field
            }
            private set
    }

    fun getCurrentWeather(latitude: Double?, longitude: Double?, unit: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.e(TAG, "getCurrentWeather: $latitude")
                Log.e(TAG, "getCurrentWeather: $longitude")
                Log.e(TAG, "getCurrentWeather: $unit" )
                val callResponse = apiService.getCurrentWeather(latitude,longitude, unit, API_KEY)
                callResponse.enqueue(object : Callback<City>{
                    override fun onFailure(call: Call<City>, t: Throwable) {
                        t.printStackTrace()
                        currentWeatherResponse.postValue(null)
                    }

                    override fun onResponse(call: Call<City>, response: Response<City>) {
                        if (response.isSuccessful){
                            currentWeatherResponse.postValue(response.body())
                            return
                        }
                        currentWeatherResponse.postValue(null)
                    }

                })
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun getDateWiseWeather(cityId: String, unit: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val callResponse = apiService.getDateWiseWeather(cityId, unit, API_KEY)
                callResponse.enqueue(object : Callback<City>{
                    override fun onFailure(call: Call<City>, t: Throwable) {
                        t.printStackTrace()
                        dateWiseWeatherResponse.postValue(null)
                    }

                    override fun onResponse(call: Call<City>, response: Response<City>) {
                        if (response.isSuccessful){
                            dateWiseWeatherResponse.postValue(response.body())
                            return
                        }
                        dateWiseWeatherResponse.postValue(null)
                    }

                })
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    fun getWeeklyWeather(latitude: Double?, longitude: Double?, unit: String){
        CoroutineScope(Dispatchers.IO).launch {
           try {
               val callResponse = apiService.getWeeklyWeather(latitude,longitude, API_KEY, "current,minutely,hourly",unit)
               callResponse.enqueue(object : Callback<WeeklyForecast>{
                   override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                       t.printStackTrace()
                       weeklyWeatherResponse.postValue(null)
                   }

                   override fun onResponse(call: Call<WeeklyForecast>, response: Response<WeeklyForecast>) {
                       if (response.isSuccessful){
                           weeklyWeatherResponse.postValue(response.body())
                           return
                       }
                       weeklyWeatherResponse.postValue(null)
                   }

               })
           }catch (e: Exception){
               e.printStackTrace()
           }
        }
    }
}