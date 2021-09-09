package fr.tonychouteau.weatherwidget.utils;

import android.content.Context;
import android.widget.RemoteViews;

public class ContextManager {

    public Context context;
    public RemoteViews views;
    public int appWidgetId;

    public ContextManager(Context context, RemoteViews views, int appWidgetId) {
        this.context = context;
        this.views = views;
        this.appWidgetId = appWidgetId;
    }

}
