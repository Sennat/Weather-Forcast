package com.project.natnaelalemayehuweatherforcastapi.network.api

import com.project.natnaelalemayehuweatherforcastapi.constants.API_ID
import com.project.natnaelalemayehuweatherforcastapi.models.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApiService {
    // https://api.openweathermap.org/data/2.5/forecast?q={city}&appid={api key}
    @GET("/data/2.5/forecast")
    suspend fun getWeatherData(
        // Get dynamic parameters
        @Query("q") q: String,
        @Query("units") unit: String,
        @Query("appid") entity: String = API_ID
    ): Response<WeatherResponse>
}