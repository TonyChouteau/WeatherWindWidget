package fr.tonychouteau.weatherwidget.weather;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.util.function.Consumer;

import fr.tonychouteau.weatherwidget.http.AsynchImageHandler;
import fr.tonychouteau.weatherwidget.http.AsynchJsonHandler;

public class OpenWeatherHandler {

    //=================================
    // Static
    //=================================

    // Base API URL
    private final String BASE_API_URL = "https://api.openweathermap.org/data/2.5/weather/";
    private final String LANG = "lang=";
    private String langParam(String lang) {
        return LANG + lang;
    }
    private final String API = "appid=";
    private String apiParam(String apikey) {
        return API + apikey;
    }
    private final String CITY = "q=";
    private String cityParam(String city) {
        return CITY + city;
    }
    private final String METRIC = "units=metric";

    private String makeParams(String ...params) {
        String params_url = "?";
        for (String param: params) {
            params_url += param + "&";
        }
        return params_url;
    }

    // Image URLs
    private final String BASE_IMG_URL = "https://openweathermap.org/img/wn/";

    private final String PNG_URL = ".png";
    private final String X2_URL = "@2x";
    private final String X4_URL = "@4x";

    //=================================
    // Non-Static
    //=================================

    private Weather weather;
    private String lang;

    //=================================
    // Constructor
    //=================================

    public OpenWeatherHandler(Weather weather, String lang) {
        this.weather = weather;
        this.lang = lang;
    }

    //=================================
    // Public Methods
    //=================================

    public void withWeather(Consumer<JSONObject> consumer) {
        AsynchJsonHandler asyncHandler = new AsynchJsonHandler();
        asyncHandler.setConsumer(consumer);
        String api_url = BASE_API_URL + makeParams(
                cityParam(this.weather.getCity()),
                apiParam("<apikey>"),
                langParam("fr"),
                METRIC
        );
        asyncHandler.execute(api_url);
    }

    public void withIcon(Consumer<Bitmap> consumer) {
        AsynchImageHandler asynchWeather = new AsynchImageHandler();
        asynchWeather.setConsumer(consumer);
        String image_url = BASE_IMG_URL + this.weather.getIcon() + X4_URL + PNG_URL;
        asynchWeather.execute(image_url);
    }
}
