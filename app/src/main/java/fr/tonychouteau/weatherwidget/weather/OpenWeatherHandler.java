package fr.tonychouteau.weatherwidget.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

import fr.tonychouteau.weatherwidget.http.AsynchImageHandler;

public class OpenWeatherHandler {

    //=================================
    // Static
    //=================================

    // Base API URL
    private final String BASE_API_URL = "https://api.openweathermap.org/data/2.5/";

    // Image URLs
    private final String BASE_IMG_URL = "https://openweathermap.org/img/wn/";

    private final String PNG_URL = ".png";
    private final String X2_URL = "@2x";

    //=================================
    // Non-Static
    //=================================

    private Weather weather;

    //=================================
    // Constructor
    //=================================

    public OpenWeatherHandler(Weather weather) {
        this.weather = weather;
    }

    //=================================
    // Methods
    //=================================

    public void withIcon(Consumer<Bitmap> consumer) {
        AsynchImageHandler asynchWeather = new AsynchImageHandler();
        asynchWeather.setConsumer(consumer);
        String image_url = BASE_IMG_URL + this.weather.getIcon() + X2_URL + PNG_URL;
        asynchWeather.execute(image_url);
    }
}
