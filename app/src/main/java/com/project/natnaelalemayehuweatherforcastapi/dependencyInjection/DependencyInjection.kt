package com.project.natnaelalemayehuweatherforcastapi.dependencyInjection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.project.natnaelalemayehuweatherforcastapi.constants.BASE_URL
import com.project.natnaelalemayehuweatherforcastapi.network.api.WeatherApiService
import com.project.natnaelalemayehuweatherforcastapi.network.repositories.WeatherServiceRepositoryIMPL
import com.project.natnaelalemayehuweatherforcastapi.viewModel.WeatherViewModel
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DependencyInjection {

    private val service = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(provideHttpClient())
    .build()
    .create(WeatherApiService::class.java)

    private fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun provideRepository() = WeatherServiceRepositoryIMPL(service)
    private fun provideDispatcher() = Dispatchers.IO

    fun provideViewModel(storeOwner: ViewModelStoreOwner): WeatherViewModel {
        return ViewModelProvider(storeOwner, object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return WeatherViewModel(provideRepository(), provideDispatcher()) as T
            }
        })[WeatherViewModel::class.java]
    }
}
