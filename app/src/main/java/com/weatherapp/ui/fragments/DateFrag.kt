package com.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.weatherapp.R
import com.weatherapp.data.model.Cities
import com.weatherapp.data.model.IndianCities
import com.weatherapp.data.model.weather.City
import com.weatherapp.data.sharedPrefs.UserSharedPrefs
import com.weatherapp.databinding.FragmentDateBinding
import com.weatherapp.util.CommonUtils.Companion.getDateTimeFromMillisecond
import com.weatherapp.util.CommonUtils.Companion.getWindSpeedUnit
import com.weatherapp.viewmodel.DateWeatherViewModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*
import java.util.stream.Collectors


class DateFrag : Fragment() {

    private var _binding : FragmentDateBinding? = null
    private val binding get() = _binding!!
    private lateinit var userSharedPrefs: UserSharedPrefs
    private var latitude: Double? = null
    private var longitude: Double? = null
    private val degree = "°"
    private val windSpeedMetric = "meter/sec"
    private val windSpeedImperial = "miles/hour"

    lateinit var viewModel: DateWeatherViewModel
    var listCity = listOf<Cities>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDateBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProviders.of(this).get(DateWeatherViewModel::class.java)
        userSharedPrefs = UserSharedPrefs.getSharedPref(context!!)
        getCitiesList()
        subscribeObservers()
        binding.button.setOnClickListener {
            if(binding.autoComplete.text.isNotEmpty()){
                binding.progressCircular.visibility = VISIBLE
                val city: Cities? = listCity.find { cities -> cities.city == binding.autoComplete.text.toString() }
                viewModel.getDateWiseWeather(city?.ids!!,userSharedPrefs.getUnit())
            }
        }
        return view
    }

    private fun subscribeObservers(){
        viewModel.getDateWiseWeatherResponse().observe(viewLifecycleOwner, androidx.lifecycle.Observer { cityWeather ->
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
        binding.temp.text = "${cityWeather.main?.temp}$degree"
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

    private fun getCitiesList() {
        try {
            val indianCities = Gson().fromJson(getJson(),IndianCities::class.java)
            listCity = indianCities.cities
            setSpinner()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter(activity!!, R.layout.support_simple_spinner_dropdown_item,listCity.stream().map(Cities::city).collect(Collectors.toList()) as List<String>)
        binding.autoComplete.setAdapter(adapter)
        binding.autoComplete.threshold = 1
    }

    // Get the content of indian_cities.json from assets directory and store it as string
    private fun getJson(): String {
        val json: String
        json = try {
            // Opening cities.json file
            val `is`: InputStream = context?.assets?.open("indian_cities.json")!!
            // is there any content in the file
            val size: Int = `is`.available()
            val buffer = ByteArray(size)
            // read values in the byte array
            `is`.read(buffer)
            // close the stream --- very important
            `is`.close()
            // convert byte to string
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }

    companion object {
        @JvmStatic
        fun newInstance() = DateFrag()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}