package fr.tonychouteau.weatherwidget.weather.definition;

import java.util.ArrayList;

import fr.tonychouteau.weatherwidget.weather.definition.location.Coords;

public class WeatherDataContainer {

    private String city;

    private Coords coords;

    private Weather current;
    private ArrayList<Weather> history;
    private ArrayList<Weather> forecast;

    public WeatherDataContainer(String city) {
        this.city = city;
    }

    public Weather getCurrent() {
        return current;
    }

    public void setCurrent(Weather current) {
        this.current = current;
    }

    public ArrayList<Weather> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<Weather> history) {
        this.history = history;
    }

    public ArrayList<Weather> getForecast() {
        return forecast;
    }

    public void setForecast(ArrayList<Weather> forecast) {
        this.forecast = forecast;
    }

    public String getCity() {
        return city;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public String getCurrentTimestamp() {
        return this.current.formatTimestamp();
    }
}
