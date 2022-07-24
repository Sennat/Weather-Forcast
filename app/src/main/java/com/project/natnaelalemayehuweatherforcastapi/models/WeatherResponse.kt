package com.project.natnaelalemayehuweatherforcastapi.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class WeatherResponse(val list: List<WeatherData>)

@Parcelize
data class WeatherData(
    val main: Main,
    val weather: List<WeatherItem>,
    val clouds: Cloud,
    val wind: Wind,
    val pop: Double,
    val dt_txt: String
): Parcelable

@Parcelize
data class Main(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    val temp_kf: Double
): Parcelable

@Parcelize
data class WeatherItem(
    val main: String,
    val description: String,
): Parcelable

@Parcelize
data class Cloud(
    val all: Int    // Cloudiness %
): Parcelable

@Parcelize
data class Wind(
    val speed: Double,  // wind speed m/s
): Parcelable

