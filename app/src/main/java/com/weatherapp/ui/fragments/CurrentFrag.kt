package com.weatherapp.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.weatherapp.data.model.weather.City
import com.weatherapp.data.sharedPrefs.UserSharedPrefs
import com.weatherapp.databinding.FragmentCurrentBinding
import com.weatherapp.util.CommonUtils.Companion.getDateTimeFromMillisecond
import com.weatherapp.util.CommonUtils.Companion.getWindSpeedUnit
import com.weatherapp.viewmodel.CurrentWeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CurrentFrag"

class CurrentFrag : Fragment(), LocationListener {

    private var _binding: FragmentCurrentBinding? = null

    private val binding get() = _binding!!

    private var latitude: Double? = null
    private var longitude: Double? = null
    private val degree = "°"

    lateinit var viewModel: CurrentWeatherViewModel
    lateinit var userSharedPrefs: UserSharedPrefs
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCurrentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProviders.of(this).get(CurrentWeatherViewModel::class.java)
        userSharedPrefs = UserSharedPrefs.getSharedPref(context!!)

        getLocation()
        subscribeObservers()
        return view
    }

    private fun subscribeObservers(){
        viewModel.getCurrentWeatherResponse().observe(viewLifecycleOwner, Observer { cityWeather ->
            Log.e(TAG, "subscribeObservers: $cityWeather" )
            binding.progressCircular.visibility = GONE
            if (cityWeather != null) {
                binding.llWeather.visibility = VISIBLE
                setWeather(cityWeather)
            }

        })
    }

    private fun setWeather(cityWeather: City) {
        binding.username.text = "Hello, ${userSharedPrefs.getUsername()}"
        binding.weather.text = cityWeather.weather[0].main
//        binding.weatherDesc.text = cityWeather.weather[0].description
        binding.area.text = "${cityWeather.name}, ${cityWeather.sys?.country}"
        binding.temp.text = "${cityWeather.main?.temp}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.minTemp.text = "Min. ${cityWeather.main?.tempMin}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.maxTemp.text = "Max. ${cityWeather.main?.tempMax}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.realFeel.text = "${cityWeather.main?.feelsLike}$degree${if(userSharedPrefs.getUnit() == "metric") "C" else "F"}"
        binding.windSpeed.text = "${cityWeather.wind.speed} ${getWindSpeedUnit(userSharedPrefs)}"
        binding.humidity.text = "${cityWeather.main?.humidity}%"
        binding.pressure.text = "${cityWeather.main?.pressure} hPa"
        binding.cloudiness.text = "${cityWeather.clouds?.all}%"
        binding.sunrise.text = getDateTimeFromMillisecond(cityWeather.sys?.sunrise?.toInt())
        binding.sunset.text = getDateTimeFromMillisecond(cityWeather.sys?.sunset?.toInt())

        Glide.with(this)
                .load("http://openweathermap.org/img/wn/${cityWeather.weather[0].icon}@4x.png")
                .into(binding.weatherIcon)

    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 115)
            return
        }
        val locationManager = context!!.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val provider = locationManager.getBestProvider(criteria, true)!!
        val location = locationManager.getLastKnownLocation(provider)
        if (location != null) {
            onLocationChanged(location)
        } else {
            locationManager.requestLocationUpdates(provider, 20000, 0f, this)
        }
    }

    override fun onLocationChanged(location: Location) {
        longitude = location.longitude
        latitude = location.latitude

        viewModel.getCurrentWeather(latitude,longitude,userSharedPrefs.getUnit())

    }

    companion object {
        @JvmStatic
        fun newInstance() = CurrentFrag()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
}