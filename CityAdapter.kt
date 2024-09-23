// CityAdapter.kt
package com.example.myintentapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myintentapplication.databinding.ItemCityBinding

class CityAdapter(private var cityList: List<City>) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cityList[position])
    }

    override fun getItemCount(): Int = cityList.size

    class CityViewHolder(private val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            binding.cityNameTextView.text = city.name
            binding.countryTextView.text = city.country
        }
    }

    // Método para atualizar a lista de cidades
    fun updateCities(newCityList: List<City>) {
        cityList = newCityList
        notifyDataSetChanged()  // Notifica o RecyclerView sobre as mudanças
    }
}
