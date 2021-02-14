package com.weatherapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weatherapp.data.model.weeklyForecast.Daily
import com.weatherapp.data.sharedPrefs.UserSharedPrefs
import com.weatherapp.databinding.ItemWeatherBinding
import com.weatherapp.ui.activity_base.SelectedWeatherActivity
import com.weatherapp.util.CommonUtils.Companion.getDateFromMillisecond
import com.weatherapp.util.CommonUtils.Companion.getDateTimeFromMillisecond
import java.io.Serializable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class WeeklyWeatherAdapter :
    ListAdapter<Daily, WeeklyWeatherAdapter.WeeklyWeatherViewHolder>(WeeklyWeatherDiffUtils()) {

    lateinit var cityData: Daily

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyWeatherViewHolder {

        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeeklyWeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeeklyWeatherViewHolder, position: Int) {
        holder.bind(position, holder.itemView.context)
    }

    inner class WeeklyWeatherViewHolder(val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, context: Context) {
            val item = getItem(position)
            Glide.with(context)
                .load("http://openweathermap.org/img/wn/${item.weather!![0].icon}@2x.png")
                .into(binding.weatherIcon)

            binding.weather.text = item.weather!![0].main
            binding.date.text = getDateFromMillisecond(item.dt?.toInt())
            binding.temp.text = "${item.temp?.day}°${
                if (UserSharedPrefs.getSharedPref(context).getUnit() == "metric") "C" else "F"
            }"

            binding.root.setOnClickListener {
                context.startActivity(
                    Intent(context, SelectedWeatherActivity::class.java).putExtra(
                        "weather_data",
                        item as Serializable
                    )
                )
            }

        }
    }


    class WeeklyWeatherDiffUtils : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(
            oldItemPosition: Daily,
            newItemPosition: Daily
        ): Boolean {
            return oldItemPosition.dt == newItemPosition.dt

        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.dt == newItem.dt
        }

    }

}