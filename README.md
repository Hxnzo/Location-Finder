# LocationFinder App

## Overview
LocationFinder is an Android app designed to manage and interact with location data for areas within the Greater Toronto Area (GTA). The app enables users to add, view, edit, and delete locations. Additionally, users can query the database for latitude and longitude information for a given address and load a preset list of coordinates from a text file located in the assets folder.

## Features

### 1. Database Setup and Location Table
- The app connects to a local database with a `location` table that includes the following columns:
  - **ID**: Unique identifier for each location entry.
  - **Address**: Address associated with a given location.
  - **Latitude**: Latitude coordinate of the location.
  - **Longitude**: Longitude coordinate of the location.

### 2. Main Functionalities
- **Query Location**: Search for a specific address in the database to retrieve its latitude and longitude coordinates.
- **Add Location**: Add a new location entry to the database, specifying the address, latitude, and longitude.
- **Update Location**: Modify an existing location’s address, latitude, or longitude.
- **Delete Location**: Remove a location entry from the database.

## Components

### MainActivity.java
This is the main screen that appears when the user opens the app. It displays a list of all saved locations, which can be clicked to edit or delete entries. Users can also add a new location by clicking the **"Add Location"** button, which navigates them to the Add Location screen.

### AddLocationActivity.java
This screen allows users to add, edit, or delete locations:
- **Add**: Users can fill out a form with address, latitude, and longitude details, then click **"Save Location"** to store it in the database.
- **Edit**: Pre-fills the form with data from the database, allowing users to make modifications and save changes.
- **Delete**: Users can remove a selected location from the database with a delete button.

### DatabaseHelper.java
Handles all database operations, including creating the database and performing add, update, and delete actions on location entries.

### GeocodingService.java
Provides a geocoding feature that converts latitude and longitude coordinates into a readable address. This service returns the first matched address for a given coordinate or **"Unknown Address"** if no match is found.

### Location.java
A helper class used as a getter and setter for the location’s properties (ID, address, latitude, and longitude), facilitating data manipulation within the app.

### Coordinate Loader
The app includes a **"Load from Files"** button, which loads latitude and longitude values from a file named **"coordinate.txt"** in the assets folder. This file should contain the coordinates of various locations in the GTA. The default file can be modified, or a new file can be added to the assets folder for custom data.

## Setup
1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Set up a local or cloud-based database and ensure the **DatabaseHelper.java** file points to the correct database source.
4. (Optional) Add a custom coordinate file in the assets folder if you'd like to load different latitude and longitude pairs.

## Usage
- Launch the app to view a list of locations.
- Use the **Add Location** button to add new locations.
- Click any location entry to view, edit, or delete.
- Use the **Load from Files** button to load preset locations from the `coordinate.txt` file. (Ensure file is in the correct folder)

