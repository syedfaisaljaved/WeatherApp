package com.weatherapp.util

import com.weatherapp.data.sharedPrefs.UserSharedPrefs
import java.text.SimpleDateFormat
import java.util.*

class CommonUtils {

    companion object{

        private const val windSpeedMetric = "meter/sec"
        private const val windSpeedImperial = "miles/hour"

        fun getDateTimeFromMillisecond(timeStamp: Int?): String? {
            if (timeStamp != null) {
                // convert seconds to milliseconds
                val date = Date(timeStamp*1000L)
                val sdf = SimpleDateFormat("HH:mm:ss")
                sdf.timeZone = TimeZone.getTimeZone("GMT+530")
                return sdf.format(date)
            }
            return "not available"
        }

        fun getDateFromMillisecond(timeStamp: Int?): String? {
            if (timeStamp != null) {
                // convert seconds to milliseconds
                val date = Date(timeStamp*1000L)
                val sdf = SimpleDateFormat("dd MMM yy")
                sdf.timeZone = TimeZone.getTimeZone("GMT+530")
                return sdf.format(date)
            }
            return "not available"
        }

        fun getWindSpeedUnit(userSharedPrefs: UserSharedPrefs): String {
            return if (userSharedPrefs.getUnit() == "metric")
                windSpeedMetric
            else
                windSpeedImperial
        }
    }
}