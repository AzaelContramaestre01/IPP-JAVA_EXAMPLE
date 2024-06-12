package com.example.weatherapp;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class CityWeatherActivity extends AppCompatActivity {

    private static final String TAG = "CityWeatherActivity";

    private TextView textTemperature;
    private TextView textWeatherCondition;
    private ImageView imageWeatherIcon;
    private ImageView backgroundImage; // Nuevo ImageView para el fondo
    private RelativeLayout mainContainer;
    private OkHttpClient client = new OkHttpClient();
    private String apiKey = "APIKEY"; // Actualizar con la nueva API key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        // Configurar la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Inicializar vistas
        textTemperature = findViewById(R.id.textTemperature);
        textWeatherCondition = findViewById(R.id.textWeatherCondition);
        imageWeatherIcon = findViewById(R.id.imageWeatherIcon);
        backgroundImage = findViewById(R.id.backgroundImage); // Inicializar nuevo ImageView
        mainContainer = findViewById(R.id.mainContainer);

        // Obtener el nombre de la ciudad del intent
        String city = getIntent().getStringExtra("city");

        // Cambiar el fondo de pantalla según la ciudad
        setCityBackground(city);

        // Obtener datos del clima para la ciudad
        fetchWeatherData(city);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Cambiar el fondo de pantalla según la ciudad
    private void setCityBackground(String city) {
        int backgroundResId;
        switch (city) {
            case "Santiago":
                backgroundResId = R.drawable.santiago;
                break;
            case "Punta Arenas":
                backgroundResId = R.drawable.punta_arenas;
                break;
            case "Puerto Natales":
                backgroundResId = R.drawable.puerto_natales;
                break;
            case "Temuco":
                backgroundResId = R.drawable.temuco;
                break;
            default:
                backgroundResId = R.drawable.stormy;
                break;
        }
        backgroundImage.setImageResource(backgroundResId);

        // Aplicar filtro blanco y negro a la imagen de fondo
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        backgroundImage.setColorFilter(filter);
    }

    // Obtener datos del clima usando OpenWeatherMap API
    private void fetchWeatherData(String city) {
        String lat = "";
        String lon = "";

        switch (city) {
            case "Santiago":
                lat = "-33.4489";
                lon = "-70.6693";
                break;
            case "Punta Arenas":
                lat = "-53.1638";
                lon = "-70.9171";
                break;
            case "Puerto Natales":
                lat = "-51.7236";
                lon = "-72.4875";
                break;
            case "Temuco":
                lat = "-38.7359";
                lon = "-72.5904";
                break;
        }

        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey + "&units=metric";
        Log.d(TAG, "Request URL: " + url);

        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e(TAG, "Error fetching weather data", e);
                runOnUiThread(() -> Toast.makeText(CityWeatherActivity.this, "Error fetching weather data", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d(TAG, "Response data: " + responseData);
                    runOnUiThread(() -> updateUI(responseData));
                } else {
                    Log.e(TAG, "Error response code: " + response.code());
                    runOnUiThread(() -> Toast.makeText(CityWeatherActivity.this, "Error fetching weather data", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    // Actualizar la UI con los datos del clima obtenidos
    private void updateUI(String responseData) {
        JsonObject jsonObject = JsonParser.parseString(responseData).getAsJsonObject();
        JsonObject main = jsonObject.getAsJsonObject("main");
        JsonObject weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();

        // Obtener la temperatura y la condición del clima
        String temp = main.get("temp").getAsString() + "°C";
        String weatherCondition = weather.get("main").getAsString();

        // Mostrar la temperatura
        textTemperature.setText(temp);

        // Mostrar la condición del clima en español
        String conditionText = "";
        switch (weatherCondition) {
            case "Rain":
                conditionText = getString(R.string.condition_rainy);
                break;
            case "Clouds":
                conditionText = getString(R.string.condition_clouds);
                break;
            case "Clear":
                conditionText = getString(R.string.condition_sunny);
                break;
            default:
                conditionText = weatherCondition;
                break;
        }
        textWeatherCondition.setText(conditionText);

        // Actualizar el icono del clima
        updateWeatherIcon(weatherCondition);
    }

    // Actualizar el icono del clima según la condición
    private void updateWeatherIcon(String condition) {
        int iconResId;
        switch (condition) {
            case "Rain":
                iconResId = R.drawable.rainy;
                break;
            case "Clouds":
                iconResId = R.drawable.cloudy;
                break;
            case "Clear":
                iconResId = R.drawable.sunny;
                break;
            default:
                iconResId = R.drawable.stormy;
                break;
        }
        imageWeatherIcon.setImageResource(iconResId);
    }
}
