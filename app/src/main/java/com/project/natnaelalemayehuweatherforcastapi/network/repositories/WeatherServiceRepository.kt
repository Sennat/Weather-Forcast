package com.project.natnaelalemayehuweatherforcastapi.network.repositories

import com.project.natnaelalemayehuweatherforcastapi.models.UIState
import com.project.natnaelalemayehuweatherforcastapi.network.api.WeatherApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface WeatherServiceRepository{
    suspend fun getWeatherData(
        q: String,
        units: String,
        appid: String) : Flow<UIState>
}


class WeatherServiceRepositoryIMPL(private val service: WeatherApiService) : WeatherServiceRepository{
    override suspend fun getWeatherData(q: String, units: String, appid: String): Flow<UIState> =
        flow{
            try {
                val res = service.getWeatherData(q, units, appid)
                if (res.isSuccessful) {
                    emit(res.body()?.let {
                        UIState.Success(it)
                    } ?: throw Exception("No data response"))
                } else {
                    throw Exception("The given city was not found")
                }
            } catch(e: Exception){
                emit(UIState.Error(e))
            }
        }
}