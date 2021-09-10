package fr.tonychouteau.weatherwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.tonychouteau.weatherwidget.utils.ContextManager;
import fr.tonychouteau.weatherwidget.utils.ViewManager;
import fr.tonychouteau.weatherwidget.weather.Weather;

/**
 * Implementation of App Widget functionality.
 */
public class Widget extends AppWidgetProvider {

    private ContextManager contextManager;
    private ViewManager viewManager;

    //=================================
    // Update Widget
    //=================================

    public void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        contextManager = new ContextManager(context, views, appWidgetId);
        viewManager = new ViewManager(contextManager);

        initOnClickEvent();
        updateWeather();

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    //=================================
    // Update Version

    private void updateVersionTime() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy h:mm:ss");
        contextManager.views.setTextViewText(R.id.last_update, format.format(currentTime));
    }

    //=================================
    // Update Weather

    private void updateWeather() {
        Weather weather = new Weather("Lannion");

        weather.updateWeather(() -> {
            weather.getIconImage(() -> {
                this.contextManager.views.setTextViewText(R.id.wind_speed, weather.getWindSpeed());
                this.contextManager.views.setTextViewText(R.id.wind_direction, weather.getWindDirection());

                this.viewManager.updateImageView(R.id.sky_view, weather.getIconImage());

                updateVersionTime();
            });
        });
    }

    //=================================
    // On Click
    //=================================

    public void initOnClickEvent() {
        Intent intentUpdate = new Intent(contextManager.context, Widget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] idArray = new int[]{contextManager.appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        PendingIntent pendingUpdate = PendingIntent.getBroadcast(
                contextManager.context, contextManager.appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT);
        contextManager.views.setOnClickPendingIntent(R.id.widget_container, pendingUpdate);
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