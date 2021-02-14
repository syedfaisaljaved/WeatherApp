package com.weatherapp.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherapp.data.sharedPrefs.UserSharedPrefs
import com.weatherapp.databinding.FragmentWeekReportBinding
import com.weatherapp.ui.adapter.WeeklyWeatherAdapter
import com.weatherapp.viewmodel.WeeklyWeatherViewModel

private const val TAG = "WeekReportFrag"

class WeekReportFrag : Fragment(), LocationListener {

    private var _binding: FragmentWeekReportBinding? = null
    private val binding get() = _binding!!

    private var latitude: Double? = null
    private var longitude: Double? = null

    lateinit var viewModel: WeeklyWeatherViewModel
    lateinit var userSharedPrefs: UserSharedPrefs
    lateinit var adapter: WeeklyWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWeekReportBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProviders.of(this).get(WeeklyWeatherViewModel::class.java)
        userSharedPrefs = UserSharedPrefs.getSharedPref(context!!)

        getLocation()
        initRecyclerView()
        subscribeObservers()
        return view
    }

    private fun initRecyclerView() {
        adapter = WeeklyWeatherAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(context!!)
        binding.recyclerView.adapter = adapter
    }

    private fun subscribeObservers(){
        viewModel.getWeeklyWeatherResponse().observe(viewLifecycleOwner, Observer {
            Log.e(TAG, "subscribeObservers: $it" )
            if (it != null) {
                adapter.submitList(it.daily)
            }
        })
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

        viewModel.getWeeklyWeather(latitude,longitude,userSharedPrefs.getUnit())
    }

    companion object {
        @JvmStatic
        fun newInstance() = WeekReportFrag()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}