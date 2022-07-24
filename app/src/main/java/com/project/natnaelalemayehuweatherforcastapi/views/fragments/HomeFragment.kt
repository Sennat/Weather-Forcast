package com.project.natnaelalemayehuweatherforcastapi.views.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.project.natnaelalemayehuweatherforcastapi.R
import com.project.natnaelalemayehuweatherforcastapi.databinding.FragmentHomeBinding

class HomeFragment : ViewModelFragment() {

    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        toggleUnits()
        getWeatherForecastByCity()

        return binding.root
    }

    /**
     *  get weather Forecast by city
     */
    private fun getWeatherForecastByCity() {
        binding.btnLookup.setOnClickListener {
            viewModelFragment.setLoading()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToWeatherListFragment())
        }
    }

    /**
     * A function to switch conversion operation
     */
    private fun toggleUnits() {
        binding.btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.btnSwitch.getThumbDrawable().setTint(Color.argb(253, 253, 153, 0))
            if (isChecked) {
                binding.txtUnit.apply {
                    binding.txtUnit.text = "Celsius"
                    binding.txtUnit.setTextColor(Color.RED)
                }
            } else {
                binding.txtUnit.apply {
                    binding.txtUnit.text = "Fahrenheit"
                    binding.txtUnit.setTextColor(Color.RED)
                }
            }
                /*binding.txtUnit.apply {
                    binding.txtUnit.text =
                        binding.root.resources.getText(R.string.fahrenheit_celsius)
                    binding.txtUnit.setTextColor(Color.BLACK)

            }*/
        }
    }
}