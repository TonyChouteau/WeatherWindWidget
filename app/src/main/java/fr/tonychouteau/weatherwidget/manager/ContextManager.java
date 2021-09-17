package fr.tonychouteau.weatherwidget.manager;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.widget.RemoteViews;

public class ContextManager {

    public Context context;
    public RemoteViews views;
    public int appWidgetId;
    public AppWidgetManager appWidgetManager;

    public ContextManager(Context context, RemoteViews views, int appWidgetId,
                          AppWidgetManager appWidgetManager) {
        this.context = context;
        this.views = views;
        this.appWidgetId = appWidgetId;
        this.appWidgetManager = appWidgetManager;
    }

}
