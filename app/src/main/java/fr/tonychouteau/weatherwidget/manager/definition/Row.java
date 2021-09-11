package fr.tonychouteau.weatherwidget.manager.definition;

import android.widget.RemoteViews;

public class Row {

    static public int SIZE = 3;
    private int id;
    private RemoteViews rowView;
    private RemoteViews[] textViews;

    public Row(RemoteViews rowView, RemoteViews[] textViews) {
        this.rowView = rowView;
        this.textViews = textViews;
    }

    public RemoteViews getRowView() {
        return this.rowView;
    }

    public RemoteViews getTextView(int id) {
        return textViews[id];
    }
}
