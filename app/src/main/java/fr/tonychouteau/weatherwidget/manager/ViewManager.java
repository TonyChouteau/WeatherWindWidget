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

public class ViewManager {

    private AppWidgetTarget appWidgetTarget;
    private ContextManager contextManager;

    private Table table;

    public ViewManager(ContextManager contextManager, Table table) {
        this.contextManager = contextManager;
        this.table = table;
    }

    public void updateImageView(int imageView, Bitmap image) {
        appWidgetTarget = new AppWidgetTarget(contextManager.context, imageView, contextManager.views, contextManager.appWidgetId) {
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

    public void updateAppWidget() {
        this.contextManager.appWidgetManager.updateAppWidget(
                this.contextManager.appWidgetId,
                this.contextManager.views
        );
    }

    public void updateVersionTime() {
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy h:mm:ss", Locale.FRANCE);
        this.setText(R.id.last_update, format.format(currentTime));
    }

//    public int getRessourceId(String string_id) {
//        return this.contextManager.context.getResources().getIdentifier(string_id, "id", this.contextManager.context.getPackageName());
//    }

    public void makeRow() {
        RemoteViews rowView = new RemoteViews(this.contextManager.context.getPackageName(), R.layout.row);

        RemoteViews[] cells = new RemoteViews[3];
        RemoteViews[] textViews = new RemoteViews[3];
        for (int i = 0; i < Row.SIZE; i++) {
            cells[i] = new RemoteViews(this.contextManager.context.getPackageName(), R.layout.cell);
            textViews[i] = new RemoteViews(this.contextManager.context.getPackageName(), R.layout.text);
            cells[i].addView(R.id.cell, textViews[i]);
            rowView.addView(R.id.row, cells[i]);
        }

        this.table.addRow(rowView, textViews);
    }

    public void updateTable(String speed, String direction) {

        this.makeRow();
        if (this.table.size() > 3) {
            this.table.clear();
        }

        this.contextManager.views.removeAllViews(R.id.list_container);
        this.table.forEach(row -> {
            this.contextManager.views.addView(R.id.list_container, row.getRowView());
            row.getTextView(0).setTextViewText(R.id.text, speed);
            row.getTextView(1).setTextViewText(R.id.text, direction);
        });
    }
}
