package fr.tonychouteau.weatherwidget.manager;

import android.graphics.Bitmap;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.tonychouteau.weatherwidget.R;
import fr.tonychouteau.weatherwidget.manager.definition.Row;
import fr.tonychouteau.weatherwidget.manager.definition.Table;
import fr.tonychouteau.weatherwidget.remote.file.ImageHandler;
import fr.tonychouteau.weatherwidget.weather.definition.Weather;

public class ViewManager {

    private AppWidgetTarget appWidgetTarget;
    private ContextManager contextManager;

    private Table table;

    //=================================
    // Constructor
    //=================================

    public ViewManager(ContextManager contextManager, Table table) {
        this.contextManager = contextManager;
        this.table = table;
    }

    //=================================
    // Utils
    //=================================

    public void updateImageView(RemoteViews views, int imageView, Bitmap image) {
        appWidgetTarget = new AppWidgetTarget(contextManager.context, imageView, views, contextManager.appWidgetId) {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                super.onResourceReady(resource, transition);
            }
        };

        Glide.with(contextManager.context.getApplicationContext())
                .asBitmap()
                .load(image)
                .into(appWidgetTarget);
    }

    public void setText(int textView, String text) {
        this.contextManager.views.setTextViewText(textView, text);
    }

    //=================================
    // Update
    //=================================

    public void updateVersionTime() {
        Date currentTime = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM h:mm", Locale.FRANCE);
        this.setText(R.id.last_update, format.format(currentTime));
    }

//    public int getRessourceId(String string_id) {
//        return this.contextManager.context.getResources().getIdentifier(string_id, "id", this.contextManager.context.getPackageName());
//    }

    public void displayCurrentWether(Weather weather) {
        this.updateImageView(this.contextManager.views, R.id.sky_view, ImageHandler.getBitmapFromAsset(this.contextManager.context, weather.getSkyViewPath()));
        this.contextManager.views.setTextViewText(R.id.current_speed, weather.formatWindSpeed());
        this.contextManager.views.setTextViewText(R.id.current_direction, weather.formatWindDirection());
    }

    public void updateAppWidget() {
        this.contextManager.appWidgetManager.updateAppWidget(
                this.contextManager.appWidgetId,
                this.contextManager.views
        );
    }

    //=================================
    // Handle Table
    //=================================

    public void makeRow() {
        RemoteViews rowView = new RemoteViews(this.contextManager.context.getPackageName(), R.layout.row);

        this.table.addRow(rowView);
    }

    public void updateTable(String speed, String direction) {

        makeRow();
        makeRow();
        makeRow();
        makeRow();

        this.contextManager.views.removeAllViews(R.id.list_container_forecast);
        this.table.forEach(row -> {
            RemoteViews rowView =  row.getRowView();
            this.contextManager.views.addView(R.id.list_container_forecast, rowView);
            rowView.setTextViewText(R.id.text_1, speed);
            rowView.setTextViewText(R.id.text_2, direction);
            rowView.setTextViewText(R.id.text_2, direction);
        });
    }
}
