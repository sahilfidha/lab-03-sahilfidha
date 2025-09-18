package com.example.listycitylab3;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AddCityFragment.AddCityDialogListener{

    private ArrayList<City> dataList;
    private ListView cityList;
    private ArrayAdapter<City> cityAdapter;

    private City selectedCity = null;
    private int selectedCityPos =-1;

    @Override
    public void addCity(City city) {
        cityAdapter.add(city);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public void editCity(City newCity, int pos){
        dataList.set(pos, newCity);
        cityAdapter.notifyDataSetChanged();
        selectedCity = null;
        selectedCityPos = -1;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] cities = { "Edmonton", "Vancouver", "Toronto" };
        String[] provinces = { "AB", "BC", "ON" };


        dataList = new ArrayList<City>();
        for (int i = 0; i < cities.length; i++) {
            dataList.add(new City(cities[i], provinces[i]));
        }
        
        cityList = findViewById(R.id.city_list);
        cityAdapter = new CityArrayAdapter(this, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                selectedCity = dataList.get(pos);
                selectedCityPos = pos;
            }
        });

        FloatingActionButton fab = findViewById(R.id.button_add_city);
        fab.setOnClickListener(v -> {
            if (selectedCity != null && selectedCityPos != -1) {
                AddCityFragment.newInstance(selectedCity, selectedCityPos).show(getSupportFragmentManager(), "editCity");
            } else {
                AddCityFragment.newInstance().show(getSupportFragmentManager(), "addCity");
            }
        });
    }
}