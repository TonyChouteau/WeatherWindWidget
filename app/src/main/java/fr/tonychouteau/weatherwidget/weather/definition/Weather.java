package fr.tonychouteau.weatherwidget.weather.definition;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.tonychouteau.weatherwidget.weather.ApiHelper;
import fr.tonychouteau.weatherwidget.weather.definition.wind.Wind;

public class Weather {

    //=================================
    // NON-STATIC
    //=================================

    private String iconId;
    private String skyViewPath;

    private Wind wind;
    private Date date;

    //=================================
    // Constructor
    //=================================

    public Weather() {

    }

    //=================================
    // Methods
    //=================================

    public void updateWeather(String iconId, double windSpeed, int windDirection, Date date) {
        this.date = date;
        this.iconId = iconId;
        this.skyViewPath = "skyviews/" + iconId.replace("n", "d") + ApiHelper.X2 + ApiHelper.PNG;
        this.wind = new Wind(windSpeed, windDirection);
    }

    //=================================
    // Getters & Setters
    //=================================

    public String getIcon() {
        return iconId;
    }

    public String getWindSpeed() {
        return String.valueOf(this.wind.speed);
    }

    public String getWindDirection() {
        return String.valueOf(this.wind.direction);
    }

    public String getSkyViewPath() {
        return skyViewPath;
    }

    //=================================
    // Formating
    //=================================

    public String formatTimestamp() {
        return String.format(Locale.FRANCE, "%.0f", Math.floor(this.date.getTime() / 1000d));
    }

    public String formatWindSpeed() {
        return String.format(Locale.FRANCE, "%.1f km/h", this.wind.speed * 3.6);
    }

    public String formatShortWindSpeed() {
        return String.format(Locale.FRANCE, "%.1f", this.wind.speed * 3.6);
    }

    public String formatWindDirection() {
        return String.format(Locale.FRANCE, "%s", this.wind.direction.name);
    }

    public String formatDate(String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.FRANCE);
        return format.format(this.date);
    }
}
