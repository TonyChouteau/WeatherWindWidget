package fr.tonychouteau.weatherwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.RemoteViews;

import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        updateVersionTime();
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
        Weather weather = new Weather();

        weather.api.withIcon(bitmap -> {
            this.viewManager.updateImageView(R.id.sky_view, bitmap);
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