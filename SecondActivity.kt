package com.example.myintentapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myintentapplication.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_second)

        // Configurar o Spinner com a lista de nomes (Manaus, Belém, Cuiabá)
        val names = resources.getStringArray(R.array.names_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, names)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.nameSpinner.adapter = adapter

        // Configurar o clique do botão para exibir um Toast com os dados e retornar os dados
        binding.finishButton.setOnClickListener {
            val selectedName = binding.nameSpinner.selectedItem.toString()
            val country = binding.countryEditText.text.toString()

            // Exibir um Toast com os dados do usuário
            Toast.makeText(this, "Cidade: $selectedName, Country: $country", Toast.LENGTH_LONG).show()

            // Criar um Intent para retornar os dados para a MainActivity
            val resultIntent = Intent()
            resultIntent.putExtra("name", selectedName)
            resultIntent.putExtra("country", country)
            setResult(RESULT_OK, resultIntent)

            // Finalizar a atividade
            finish()
        }
    }
}
