package fr.tonychouteau.weatherwidget.weather;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

import fr.tonychouteau.weatherwidget.http.AsynchImageHandler;
import fr.tonychouteau.weatherwidget.http.AsynchJsonHandler;
import fr.tonychouteau.weatherwidget.http.UrlHelper;
import fr.tonychouteau.weatherwidget.weather.definition.Weather;

public class OpenWeatherHandler {

    //=================================
    // Non-Static
    //=================================

    private Weather weather;
    private String apiKey;

    private UrlHelper weatherNowUrl;
    private UrlHelper skyViewUrl;

    //=================================
    // Constructor
    //=================================

    public OpenWeatherHandler(String apiKey) {
        this.apiKey = apiKey;
        this.weather = new Weather("Lannion");

        this.weatherNowUrl = new UrlHelper(ApiHelper.API_URL)
                .param(ApiHelper.CITY, this.weather.getCity())
                .param(ApiHelper.API_KEY, this.apiKey)
                .param(ApiHelper.UNITS, ApiHelper.METRIC);

        this.skyViewUrl = new UrlHelper(ApiHelper.IMG_URL)
                .add("icon", this.weather.getIcon())
                .add(ApiHelper.X4_URL)
                .add(ApiHelper.PNG_URL);
    }

    //=================================
    // Public Methods
    //=================================

    public void withWeather(Consumer<Weather> consumer) {
        AsynchJsonHandler asyncHandler = new AsynchJsonHandler();
        asyncHandler.setConsumer(json -> {
            if (json == null) return;

            try {
                JSONObject weather = (JSONObject) json.getJSONArray("weather").get(0);
                String iconId = weather.getString("icon");

                JSONObject wind = json.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");
                int windDirection = wind.getInt("deg");

                this.weather.updateWeather(iconId, windSpeed, windDirection);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (this.weather.skyViewChanged()) {
                this.withSkyView(skyView -> {
                    this.weather.updateSkyView(skyView);

                    consumer.accept(this.weather);
                });
            } else {
                consumer.accept(this.weather);
            }
        });
        asyncHandler.execute(this.weatherNowUrl.make());
    }

    public void withSkyView(Consumer<Bitmap> consumer) {
        AsynchImageHandler asynchWeather = new AsynchImageHandler();
        asynchWeather.setConsumer(consumer);
        asynchWeather.execute(skyViewUrl
                .changePath("icon", this.weather.getIcon())
                .make());
    }
}
