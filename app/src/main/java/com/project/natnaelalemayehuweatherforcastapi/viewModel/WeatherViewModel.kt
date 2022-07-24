package com.project.natnaelalemayehuweatherforcastapi.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.natnaelalemayehuweatherforcastapi.models.UIState
import com.project.natnaelalemayehuweatherforcastapi.models.WeatherResponse
import com.project.natnaelalemayehuweatherforcastapi.network.repositories.WeatherServiceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

const val TAG = "STATE CHECK"

class WeatherViewModel(
    private val repository: WeatherServiceRepository,
    private val dispatcher: CoroutineDispatcher) : ViewModel() {

    private val _weatherListData = MutableLiveData<UIState>()
    val weatherListData: LiveData<UIState> get() = _weatherListData

    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG, "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}",throwable)
        }
    }

    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    var shouldLoadId = false

    fun getWeatherForecast(
        q: String,
        units: String,
        appid: String) {
        viewModelSafeScope.launch(dispatcher) {
            // collect from our flow
            repository.getWeatherData(q, units, appid).collect { state ->
                // postValue updates LiveData asynchronously
                _weatherListData.postValue(state)
            }
        }
    }

    // setValue is not asynchronous
    fun setLoading() {_weatherListData.value = UIState.Loading}
    fun getSuccessMessage() { println("Success")}
}