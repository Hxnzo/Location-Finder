package com.example.assignment2;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GeocodingService {

    private Geocoder geocoder;

    // Constructor that initializes the Geocoder
    public GeocodingService(Context context) {
        geocoder = new Geocoder(context, Locale.getDefault());
    }

    // Method to get address from latitude and longitude
    public String getAddressFromLatLong(double latitude, double longitude) throws IOException {
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
        if (addresses != null && !addresses.isEmpty()) {
            return addresses.get(0).getAddressLine(0);
        }
        return "Unknown Address";
    }
}
