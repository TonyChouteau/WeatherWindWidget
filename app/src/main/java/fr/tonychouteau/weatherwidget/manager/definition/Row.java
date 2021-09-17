package fr.tonychouteau.weatherwidget.manager.definition;

import android.widget.RemoteViews;

public class Row {

    static public int SIZE = 3;
    private RemoteViews rowView;

    public Row(RemoteViews rowView) {
        this.rowView = rowView;
    }

    public RemoteViews getRowView() {
        return this.rowView;
    }
}
