package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView lvLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvLocations = findViewById(R.id.lvLocations);
        databaseHelper = new DatabaseHelper(this);

        Button btnAddLocation = findViewById(R.id.btnAddLocation);
        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddLocationActivity.class));
            }
        });

        Button btnLoadCoordinates = findViewById(R.id.btnLoadCoordinates);
        btnLoadCoordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCoordinatesFromFile();
                updateLocationList();
            }
        });

        // Load the list when MainActivity starts
        updateLocationList();

        // Handle item clicks for editing a location
        lvLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location selectedLocation = (Location) parent.getItemAtPosition(position);

                // Pass the selected location's data to AddLocationActivity
                Intent intent = new Intent(MainActivity.this, AddLocationActivity.class);
                intent.putExtra("id", selectedLocation.getId());
                intent.putExtra("address", selectedLocation.getAddress());
                intent.putExtra("latitude", selectedLocation.getLatitude());
                intent.putExtra("longitude", selectedLocation.getLongitude());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateLocationList();  // Refresh the list after returning from AddLocationActivity
    }

    private void loadCoordinatesFromFile() {
        try {
            InputStream is = getAssets().open("coordinates.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            GeocodingService geocodingService = new GeocodingService(this);
            while ((line = reader.readLine()) != null) {
                String[] latLong = line.split(",");
                double latitude = Double.parseDouble(latLong[0]);
                double longitude = Double.parseDouble(latLong[1]);

                // Geocode the address from lat/long
                String address = geocodingService.getAddressFromLatLong(latitude, longitude);

                // Save to database
                databaseHelper.addLocation(address, latitude, longitude);
            }

            reader.close();
            Toast.makeText(this, "Coordinates loaded successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading coordinates", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLocationList() {
        List<Location> locations = databaseHelper.getAllLocations();
        ArrayAdapter<Location> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, locations);
        lvLocations.setAdapter(adapter);
    }
}
