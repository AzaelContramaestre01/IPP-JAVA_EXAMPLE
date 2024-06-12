package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textWelcome;
    private Button buttonCurrentForecast;
    private Spinner spinnerCities;
    private List<String> cities = Arrays.asList("Santiago", "Punta Arenas", "Puerto Natales", "Temuco");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        textWelcome = findViewById(R.id.textWelcome);
        buttonCurrentForecast = findViewById(R.id.buttonCurrentForecast);
        spinnerCities = findViewById(R.id.spinnerCities);

        // Configurar acción del botón de pronóstico actual
        buttonCurrentForecast.setOnClickListener(v -> {
            Toast.makeText(this, "Pronóstico actual no disponible aún", Toast.LENGTH_SHORT).show();
        });

        // Configurar Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCities.setAdapter(adapter);

        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCity = cities.get(position);
                onCitySelected(selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada
            }
        });
    }

    // Método que se llama cuando se selecciona una ciudad
    private void onCitySelected(String city) {
        Intent intent = new Intent(this, CityWeatherActivity.class);
        intent.putExtra("city", city);
        startActivity(intent);
    }
}
