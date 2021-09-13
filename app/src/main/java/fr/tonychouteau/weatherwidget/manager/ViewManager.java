package fr.tonychouteau.weatherwidget.manager;

import android.graphics.Bitmap;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import fr.tonychouteau.weatherwidget.R;
import fr.tonychouteau.weatherwidget.manager.definition.Table;
import fr.tonychouteau.weatherwidget.remote.file.ImageHandler;
import fr.tonychouteau.weatherwidget.weather.definition.Weather;

public class ViewManager {

    private AppWidgetTarget appWidgetTarget;
    private ContextManager contextManager;

    private Table forecastTable;
    private Table historyTable;

    //=================================
    // Constructor
    //=================================

    public ViewManager(ContextManager contextManager) {
        this.contextManager = contextManager;
        this.forecastTable = new Table();
        this.historyTable = new Table();
    }

    //=================================
    // Utils
    //=================================

    public void updateImageView(RemoteViews views, int imageView, Bitmap image) {
//        appWidgetTarget = new AppWidgetTarget(contextManager.context, imageView, views, contextManager.appWidgetId) {
//            @Override
//            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                super.onResourceReady(resource, transition);
//            }
//        };
//
//        Glide.with(contextManager.context.getApplicationContext())
//                .asBitmap()
//                .load(image)
//                .into(appWidgetTarget);

        views.setImageViewBitmap(imageView, image);
    }

    public void setText(int textView, String text) {
        this.contextManager.views.setTextViewText(textView, text);
    }

    //=================================
    // Handle Table
    //=================================

    public void makeRow(Table table) {
        RemoteViews rowView = new RemoteViews(this.contextManager.context.getPackageName(), R.layout.row);
        table.addRow(rowView);
    }

    public void displayTable(Table table, int list_container, ArrayList<Weather> weatherList) {
        this.contextManager.views.removeAllViews(list_container); //R.id.list_container_forecast

        table.forEach((i, row) -> {
            RemoteViews rowView =  row.getRowView();
            Weather weather = weatherList.get(i);

            this.contextManager.views.addView(list_container, rowView);
            this.updateImageView(rowView, R.id.image_in_row, ImageHandler.getBitmapFromAsset(this.contextManager.context, weather.getSkyViewPath()));
            rowView.setTextViewText(R.id.text_0, weather.formatDate("HH:mm"));
            rowView.setTextViewText(R.id.text_1, weather.formatShortWindSpeed());
            rowView.setTextViewText(R.id.text_2, weather.formatWindDirection());
        });
    }

    public void displayTable(Table table, ArrayList<Weather> weatherList, int viewId) {
        weatherList.forEach(data -> this.makeRow(table));

        this.displayTable(table, viewId, weatherList);
    }

    //=================================
    // Update
    //=================================

//    public int getRessourceId(String string_id) {
//        return this.contextManager.context.getResources().getIdentifier(string_id, "id", this.contextManager.context.getPackageName());
//    }

    public void displayCurrentWether(Weather weather) {
        this.updateImageView(this.contextManager.views, R.id.sky_view, ImageHandler.getBitmapFromAsset(this.contextManager.context, weather.getSkyViewPath()));
        this.contextManager.views.setTextViewText(R.id.current_speed, weather.formatWindSpeed());
        this.contextManager.views.setTextViewText(R.id.current_direction, weather.formatWindDirection());
        this.setText(R.id.last_update, weather.formatDate("dd-MM HH:mm"));
    }

    public void displayForecast(ArrayList<Weather> weatherList) {
        this.displayTable(this.forecastTable, weatherList, R.id.list_container_forecast);
    }

    public void displayHistory(ArrayList<Weather> weatherList) {
        this.displayTable(this.historyTable, weatherList, R.id.list_container_history);
    }

    public void updateAppWidget() {
        this.contextManager.appWidgetManager.updateAppWidget(
                this.contextManager.appWidgetId,
                this.contextManager.views
        );
    }
}
