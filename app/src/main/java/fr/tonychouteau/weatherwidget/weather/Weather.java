package fr.tonychouteau.weatherwidget.weather;

import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;

import fr.tonychouteau.weatherwidget.R;

public class Weather {

    //=================================
    // NON-STATIC
    //=================================

    private String string_icon = "10d";

    public OpenWeatherHandler api;

    //=================================
    // Constructor
    //=================================

    public Weather() {
        api = new OpenWeatherHandler(this);
    }

    //=================================
    // Methods
    //=================================

    public String getIcon() {
        return string_icon;
    }

    public void updateSkyView() {

    }
}
