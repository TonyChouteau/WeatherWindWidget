package fr.tonychouteau.weatherwidget.weather;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather {

    //=================================
    // NON-STATIC
    //=================================

    private String city;

    private String iconId;
    private Bitmap iconImage;

    private Double windSpeed;
    private Double windDirection;

    private OpenWeatherHandler api;

    //=================================
    // Constructor
    //=================================

    public Weather(String city) {
        this.city = city;

        api = new OpenWeatherHandler(this, "fr");
    }

    //=================================
    // Methods
    //=================================

    public void updateWeather(Runnable runnable) {
        this.api.withWeather(json -> {

            try {
                JSONObject weather = (JSONObject) json.getJSONArray("weather").get(0);
                this.iconId = weather.getString("icon");

                JSONObject wind = json.getJSONObject("wind");
                this.windSpeed = wind.getDouble("speed");
                this.windDirection = wind.getDouble("deg");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            runnable.run();
        });
    }

    public void getIconImage(Runnable runnable) {
        this.api.withIcon(bitmap -> {

            this.iconImage = bitmap;
            runnable.run();
        });
    }

    //=================================
    // Getters & Setters
    //=================================

    public String getIcon() {
        return iconId;
    }

    public String getCity() {
        return city;
    }

    public String getWindSpeed() {
        return String.valueOf(windSpeed);
    }

    public String getWindDirection() {
        return String.valueOf(windDirection);
    }

    public Bitmap getIconImage() {
        return iconImage;
    }
}
