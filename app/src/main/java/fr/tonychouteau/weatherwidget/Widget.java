package fr.tonychouteau.weatherwidget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.util.Date;

import fr.tonychouteau.weatherwidget.manager.ContextManager;
import fr.tonychouteau.weatherwidget.manager.ViewManager;
import fr.tonychouteau.weatherwidget.weather.OpenWeatherHandler;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    private static final int DATA_COUNT = 6;

    private ContextManager contextManager;
    private ViewManager viewManager;

    static private OpenWeatherHandler weatherHandler;
    static private int interval;
    static private Boolean firstUpdate = true;

    //=================================
    // Update Widget
    //=================================

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        if (firstUpdate) {
            weatherHandler = new OpenWeatherHandler(context.getString(R.string.api_key), DATA_COUNT);
            interval = 1;
            firstUpdate = false;
        } else {
            interval = interval % 2 + 1;
        }

        this.contextManager = new ContextManager(context, views, appWidgetId, appWidgetManager);
        this.viewManager = new ViewManager(this.contextManager);

        initOnClickEvent();
        updateWeather();
    }

    //=================================
    // Update Weather

    private void updateWeather() {
        weatherHandler.withWeatherData(weatherDataContainer -> {
            if (weatherDataContainer.currentIsValid()) {
                this.viewManager.displayCurrentWeather(weatherDataContainer.getCurrent());
            }
            if (weatherDataContainer.forecastIsValid()) {
                this.viewManager.displayForecast(weatherDataContainer.getForecast());
            }
            if (weatherDataContainer.historyIsValid()) {
                this.viewManager.displayHistory(weatherDataContainer.getHistory());
            }
            this.viewManager.displayCurrentVersion(new Date(), interval);
            this.viewManager.updateAppWidget();
        }, interval);
    }

    //=================================
    // On Click
    //=================================

    @SuppressLint("UnspecifiedImmutableFlag")
    public void initOnClickEvent() {
        Intent intentUpdate = new Intent(this.contextManager.context, Widget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] idArray = new int[]{this.contextManager.appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                this.contextManager.context, this.contextManager.appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);
        this.contextManager.views.setOnClickPendingIntent(R.id.widget_container, pendingUpdate);
    }

    //=================================
    // Default Handler
    //=================================

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}