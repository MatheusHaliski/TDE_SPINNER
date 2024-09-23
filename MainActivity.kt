package com.example.myintentapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myintentapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var cityAdapter: CityAdapter

    @Inject
    lateinit var cityDao: CityDao

    // Registrando um ActivityResultLauncher para receber o resultado da SecondActivity
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val cityName = data?.getStringExtra("name") // Cidade
            val countryName = data?.getStringExtra("country") // País

            // Adicionar a nova cidade ao banco de dados e atualizar o RecyclerView
            if (cityName != null && countryName != null) {
                lifecycleScope.launch {
                    addCity(City(name = cityName, country = countryName))
                    updateCityList()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialize o View Binding diretamente usando o método específico
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar o RecyclerView para cidades
        cityAdapter = CityAdapter(emptyList()) // Inicializa com uma lista vazia
        binding.recyclerViewCities.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewCities.adapter = cityAdapter

        // Configurar o clique do botão para redirecionar para a SecondActivity
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            resultLauncher.launch(intent)
        }

        // Botão para limpar os registros
        binding.clearButton.setOnClickListener {
            lifecycleScope.launch {
                clearAllCities()
                updateCityList()
            }
        }

        // Atualizar a lista de cidades ao abrir a aplicação
        lifecycleScope.launch {
            updateCityList()
        }
    }

    private suspend fun addCity(city: City) {
        cityDao.insert(city)
    }

    private suspend fun clearAllCities() {
        cityDao.deleteAllCities() // Chama a função que deleta todas as cidades
    }

    private suspend fun updateCityList() {
        val cities = cityDao.getAllCities()
        cityAdapter = CityAdapter(cities)
        binding.recyclerViewCities.adapter = cityAdapter
    }
}
