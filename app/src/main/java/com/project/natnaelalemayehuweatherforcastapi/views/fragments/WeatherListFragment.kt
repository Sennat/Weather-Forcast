package com.project.natnaelalemayehuweatherforcastapi.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.project.natnaelalemayehuweatherforcastapi.R
import com.project.natnaelalemayehuweatherforcastapi.adapters.WeatherViewAdapter
import com.project.natnaelalemayehuweatherforcastapi.constants.API_ID
import com.project.natnaelalemayehuweatherforcastapi.databinding.FragmentWeatherListBinding
import com.project.natnaelalemayehuweatherforcastapi.models.UIState
import com.project.natnaelalemayehuweatherforcastapi.models.WeatherData
import com.project.natnaelalemayehuweatherforcastapi.models.WeatherResponse

class WeatherListFragment : ViewModelFragment() {

    private lateinit var binding: FragmentWeatherListBinding
    private val args: WeatherListFragmentArgs by navArgs()
    private val weatherViewAdapter by lazy {WeatherViewAdapter(openDetails = ::openDetails)}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeatherListBinding.inflate(layoutInflater)
        configureObserver()
        getNavArgs()

        return binding.root
    }

    private fun configureObserver() {
        viewModelFragment.weatherListData
            .observe(viewLifecycleOwner) { uiState ->
                when (uiState) {
                    is UIState.Loading -> {
                        viewModelFragment.getWeatherForecast(args.city, args.unit, API_ID)
                    }
                    is UIState.Error -> {
                        binding.pbLoading.visibility = View.GONE
                        binding.tvLoadingText.text = uiState.error.message
                    }
                    is UIState.Success<*> -> {
                        binding.apply {
                            pbLoading.visibility = View.GONE
                            tvLoadingText.visibility = View.GONE
                            weatherViewAdapter.setWeatherList((uiState.response as WeatherResponse).list)
                            recyclerview.adapter = weatherViewAdapter
                        }
                    }
                }
            }
    }

    private fun getNavArgs() {
        when (args.unit) {
            "imperial"  -> binding.root.resources.getString(R.string.unit).apply { "°F" }
            "metric"  -> binding.root.resources.getString(R.string.unit).apply {"°C"}
        }
    }

    private fun openDetails(weatherData: WeatherData) {
        viewModelFragment.setLoading()
        findNavController().navigate(WeatherListFragmentDirections.actionWeatherListFragmentToWeatherDetailsFragment(weatherData))
    }

}