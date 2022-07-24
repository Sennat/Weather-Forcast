package com.project.natnaelalemayehuweatherforcastapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.project.natnaelalemayehuweatherforcastapi.R
import com.project.natnaelalemayehuweatherforcastapi.databinding.FragmentWeatherListBinding
import com.project.natnaelalemayehuweatherforcastapi.databinding.WeatherListItemBinding
import com.project.natnaelalemayehuweatherforcastapi.models.WeatherData
import com.project.natnaelalemayehuweatherforcastapi.views.fragments.WeatherListFragment
import com.project.natnaelalemayehuweatherforcastapi.views.fragments.WeatherListFragmentArgs
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.properties.ReadOnlyProperty

class WeatherViewAdapter(private val itemList: MutableList<WeatherData> = mutableListOf(),  private val openDetails: (WeatherData) -> Unit): RecyclerView.Adapter<WeatherViewAdapter.WeatherViewHolder>() {

    fun setWeatherList(newList: List<WeatherData>) {
        itemList.clear()
        itemList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class WeatherViewHolder(private val binding: WeatherListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: WeatherData) {
            binding.apply {
                val firstApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m:ss")
                val date = LocalDate.parse(item.dt_txt, firstApiFormat)
                txtDate.text =  date.toString()
                txtCity.text = binding.root.resources.getString(R.string.current_city)
                txtFeel.text = "Feel Like: ${item.main.feels_like.toString()} ${binding.root.resources.getString(R.string.unit)}"
                txtTemperature.text = "${item.main.temp.toString()} ${binding.root.resources.getString(R.string.unit)}"
                txtCloud.text = "Cloudiness: ${item.clouds.all.toString()}%"
                txtWind.text = "Wind: ${item.wind.speed.toString()} m/s"
                txtHumidity .text = "Humidity: ${item.main.humidity.toString()}%"

                binding.root.setOnClickListener {
                    openDetails(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(WeatherListItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}