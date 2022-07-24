package com.project.natnaelalemayehuweatherforcastapi.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.project.natnaelalemayehuweatherforcastapi.R
import com.project.natnaelalemayehuweatherforcastapi.adapters.WeatherViewAdapter
import com.project.natnaelalemayehuweatherforcastapi.constants.API_ID
import com.project.natnaelalemayehuweatherforcastapi.databinding.FragmentWeatherDetailsBinding
import com.project.natnaelalemayehuweatherforcastapi.models.UIState
import com.project.natnaelalemayehuweatherforcastapi.models.WeatherData
import com.project.natnaelalemayehuweatherforcastapi.models.WeatherItem
import com.project.natnaelalemayehuweatherforcastapi.models.WeatherResponse


class WeatherDetailsFragment : ViewModelFragment() {

    private lateinit var binding: FragmentWeatherDetailsBinding
    private val args: WeatherDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeatherDetailsBinding.inflate(layoutInflater)
        configureObserver()

        return binding.root
    }

    private fun configureObserver() {
        viewModelFragment.weatherListData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    viewModelFragment.getSuccessMessage()
                }
                is UIState.Error -> {
                    binding.apply {
                        loading.visibility = View.GONE
                        txtLoading.text = state.error.message
                    }
                }
                is UIState.Success<*> -> {
                    binding.apply {
                        loading.visibility = View.GONE
                        txtTemperature.text = "${args.weatherItem?.main?.temp?.toDouble().toString()}"
                        txtTempFeel.text = "Feel: ${ args.weatherItem?.main?.feels_like.toString()} ${binding.root.resources.getString(R.string.unit)}"
                        txtMin.text = "Min: ${args.weatherItem?.main?.temp_min.toString()} ${binding.root.resources.getString(R.string.unit)}"
                        txtMax.text = "Max: ${args.weatherItem?.main?.temp_max.toString()} ${binding.root.resources.getString(R.string.unit)}"
                        txtWindSpeed.text ="${args.weatherItem?.wind?.speed?.toInt().toString()}m/s"
                        txtHumidity.text = "${args.weatherItem?.main?.humidity.toString()}%"
                        txtCloud.text = args.weatherItem?.clouds?.all?.toInt().toString()
                    }
                }
            }
        }
    }
}