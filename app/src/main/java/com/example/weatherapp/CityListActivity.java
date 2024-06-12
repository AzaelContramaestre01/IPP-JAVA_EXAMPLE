package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class CityListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCities;
    private List<String> cities = Arrays.asList("Santiago", "Punta Arenas", "Puerto Natales", "Temuco");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        // Inicializar RecyclerView
        recyclerViewCities = findViewById(R.id.recyclerViewCities);
        recyclerViewCities.setLayoutManager(new LinearLayoutManager(this));

        // Configurar adaptador para RecyclerView
        CityAdapter cityAdapter = new CityAdapter(cities, this::onCitySelected);
        recyclerViewCities.setAdapter(cityAdapter);
    }

    // Método que se llama cuando se selecciona una ciudad
    private void onCitySelected(String city) {
        Intent intent = new Intent(this, CityWeatherActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);
    }
}
