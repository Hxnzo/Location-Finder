package com.example.assignment2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddLocationActivity extends AppCompatActivity {

    private EditText etAddress, etLatitude, etLongitude;
    private DatabaseHelper databaseHelper;
    private long locationId = -1;  // -1 means no location is being edited

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        etAddress = findViewById(R.id.etAddress);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        databaseHelper = new DatabaseHelper(this);

        // Check if we are editing an existing location
        if (getIntent() != null && getIntent().hasExtra("id")) {
            locationId = getIntent().getLongExtra("id", -1);
            String address = getIntent().getStringExtra("address");
            double latitude = getIntent().getDoubleExtra("latitude", 0);
            double longitude = getIntent().getDoubleExtra("longitude", 0);

            // Pre-fill the fields with the current location data
            etAddress.setText(address);
            etLatitude.setText(String.valueOf(latitude));
            etLongitude.setText(String.valueOf(longitude));
        }

        // Save Button: Add or Update Location
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = etAddress.getText().toString();
                double latitude = Double.parseDouble(etLatitude.getText().toString());
                double longitude = Double.parseDouble(etLongitude.getText().toString());

                if (!address.isEmpty() && etLatitude.getText().length() > 0 && etLongitude.getText().length() > 0) {
                    if (locationId == -1) {
                        // Adding a new location
                        databaseHelper.addLocation(address, latitude, longitude);
                        Toast.makeText(AddLocationActivity.this, "Location added", Toast.LENGTH_SHORT).show();
                    } else {
                        // Updating an existing location
                        databaseHelper.updateLocation(locationId, address, latitude, longitude);
                        Toast.makeText(AddLocationActivity.this, "Location updated", Toast.LENGTH_SHORT).show();
                    }
                    finish();  // Go back to MainActivity after saving
                } else {
                    Toast.makeText(AddLocationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Back Button: Discard input and return to MainActivity
        FloatingActionButton fabBack = findViewById(R.id.fabBack);
        fabBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Just finish the activity, discarding changes
            }
        });

        // Delete Button: Delete location if editing
        FloatingActionButton fabDelete = findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationId != -1) {
                    databaseHelper.deleteLocation(locationId);  // Delete the location from the database
                    Toast.makeText(AddLocationActivity.this, "Location deleted", Toast.LENGTH_SHORT).show();
                    finish();  // Go back to MainActivity after deletion
                } else {
                    Toast.makeText(AddLocationActivity.this, "Cannot delete. Not Saved in Database.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}