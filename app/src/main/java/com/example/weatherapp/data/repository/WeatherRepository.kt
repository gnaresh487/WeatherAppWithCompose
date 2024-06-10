package com.example.weatherapp.data.repository

import android.util.Log
import com.example.weatherapp.data.model.WeatherResponse
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.utils.extensions.resultOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {
    suspend fun getWeather(city: String, apiKey: String): Result<WeatherResponse> = withContext(Dispatchers.IO){
        return@withContext resultOf {
            weatherApi.getWeather(city, apiKey)
        }.onFailure {
            Log.e("TAG", "getWeather: error ", it)
        }
    }
}