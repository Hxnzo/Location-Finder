package com.example.assignment2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_LOCATIONS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ADDRESS + " TEXT, "
            + COLUMN_LATITUDE + " REAL, "
            + COLUMN_LONGITUDE + " REAL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        onCreate(db);
    }

    // Add a location to the database
    public long addLocation(String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        return db.insert(TABLE_LOCATIONS, null, values);
    }

    // Delete a location from the database
    public void deleteLocation(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATIONS, "id = ?", new String[]{String.valueOf(id)});
    }

    // Update a location in the database
    public void updateLocation(long id, String address, double latitude, double longitude) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        db.update(TABLE_LOCATIONS, values, "id = ?", new String[]{String.valueOf(id)});
    }

    // Get all locations
    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOCATIONS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                String address = cursor.getString(1);
                double latitude = cursor.getDouble(2);
                double longitude = cursor.getDouble(3);
                Location location = new Location(id, address, latitude, longitude);
                locations.add(location);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return locations;
    }
}