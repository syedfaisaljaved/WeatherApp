package com.weatherapp.data.model


import com.google.gson.annotations.SerializedName

data class IndianCities(
    @SerializedName("cities")
    var cities: List<Cities>
)

data class Cities(
    @SerializedName("city")
    var city: String,
    @SerializedName("code")
    var code: String,
    @SerializedName("ids")
    var ids: String,
    @SerializedName("lat")
    var lat: String,
    @SerializedName("lng")
    var lng: String
)