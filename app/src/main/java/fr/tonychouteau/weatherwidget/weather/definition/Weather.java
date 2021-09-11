package fr.tonychouteau.weatherwidget.weather.definition;

import android.graphics.Bitmap;

import java.util.Locale;

public class Weather {

    //=================================
    // NON-STATIC
    //=================================

    private String city;

    private String iconId;
    private Bitmap skyView;

    private Wind wind;

    private Boolean skyViewChanged;

    //=================================
    // Constructor
    //=================================

    public Weather(String city) {
        this.city = city;
        this.wind = wind;
    }

    //=================================
    // Methods
    //=================================

    public void updateWeather(String iconId, double windSpeed, int windDirection) {
        this.skyViewChanged = !(iconId.equals(this.iconId));
        this.iconId = iconId;
        this.wind = new Wind(windSpeed, windDirection);
    }

    public void updateSkyView(Bitmap skyView) {
        this.skyView = skyView;
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
        return String.valueOf(this.wind.speed);
    }

    public String getWindDirection() {
        return String.valueOf(this.wind.direction);
    }

    public Bitmap getSkyView() {
        return skyView;
    }

    public Boolean skyViewChanged() {
        return this.skyViewChanged;
    }

    //=================================
    // Formating
    //=================================

    public String formatWindSpeed() {
        return String.format(Locale.FRANCE, "%.1f km/h", this.wind.speed * 3.6);
    }

    public String formatWindDirection() {
        return String.format(Locale.FRANCE, "%s", this.wind.direction.name);
    }
}
