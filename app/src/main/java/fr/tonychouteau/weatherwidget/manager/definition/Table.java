package fr.tonychouteau.weatherwidget.manager.definition;

import android.content.Context;
import android.view.View;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.function.Consumer;

import fr.tonychouteau.weatherwidget.R;

public class Table {

    private ArrayList<Row> rows;
    private int widgetId;

    public Table() {
        rows = new ArrayList<>();
    }

    public void checkWidgetId(int widgetId) {
        if (this.widgetId != widgetId) {
            rows.clear();
        }
        this.widgetId = widgetId;
    }

    public void addRow(RemoteViews rowView, RemoteViews[] remoteViews) {
        Row row = new Row(rowView, remoteViews);
        rows.add(row);
    }

    public void clear() {
        this.rows.clear();
    }

    public void forEach(Consumer<Row> consumer) {
        for (int i = 0; i < rows.size(); i++) {
            consumer.accept(rows.get(i));
        }
    }

    public int size() {
        return rows.size();
    }

    public void forRow(int id, Consumer<Row> consumer) {
        consumer.accept(rows.get(id));
    }
}
