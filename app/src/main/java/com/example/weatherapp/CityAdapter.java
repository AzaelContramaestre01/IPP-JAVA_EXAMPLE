package com.example.weatherapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private List<String> cities;
    private OnCityClickListener listener;

    // Constructor del adaptador
    public CityAdapter(List<String> cities, OnCityClickListener listener) {
        this.cities = cities;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista del item de la ciudad
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        // Vincular datos a la vista del item
        String city = cities.get(position);
        holder.cityName.setText(city);
        holder.itemView.setOnClickListener(v -> listener.onCityClick(city));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    // ViewHolder para la vista del item de la ciudad
    static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;

        CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
        }
    }

    // Interfaz para manejar el click en una ciudad
    interface OnCityClickListener {
        void onCityClick(String city);
    }
}
