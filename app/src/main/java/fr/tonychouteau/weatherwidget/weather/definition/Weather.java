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

    private int state;

    //=================================
    // Constructor
    //=================================

    public Weather() {}

    //=================================
    // Methods
    //=================================

    public void updateWeather(String iconId, double windSpeed, int windDirection, Date date) {
        this.date = date;
        this.iconId = iconId;
        this.skyViewPath = "skyviews/" + iconId + ApiHelper.X2 + ApiHelper.PNG;
        this.wind = new Wind(windSpeed, windDirection);
        this.state = this.makeState();
    }

    public int smoothingFunction(int x) {
        return (int) Math.min(Math.max(6*((double)x-50)/Math.pow(1+Math.pow(((double)x-50)/120, 2),4)/3.14 + 50, 0), 100);
    }

    public int makeState() {
        int windState = this.smoothingFunction((int) (this.wind.speed * 100 / 7));
        int skyState = this.smoothingFunction(Math.min(7, Integer.parseInt(this.iconId.replaceAll("[dn]", ""))) * 100 / 7);
        int state = (Math.max(skyState, windState) * 3 + Math.min(skyState, windState) * 1) / 4;
        return state;
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

    public int formatState() {
        // Green : 0 255 0
        // Yellow : 255 255 0
        // Red : 255 0 0
        int r = 255 - Math.max(100 - this.state - 50, 0) * 255 / 50;
        int g = Math.min(100 - this.state, 50) * 255 / 50;

        return (r << 16) + (g << 8);
    }
}
