package fr.tonychouteau.weatherwidget.manager.definition;

import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Table {

    private final ArrayList<Row> rows;

    private int getStateImageViewId;

    public Table(int getStateImageViewId) {
        this.getStateImageViewId = getStateImageViewId;
        rows = new ArrayList<>();
    }

    public int getStateImageViewId() {
        return getStateImageViewId;
    }

    public void addRow(RemoteViews rowView) {
        Row row = new Row(rowView);
        rows.add(row);
    }

    public void forEach(BiConsumer<Integer ,Row> consumer) {
        for (int i = 0; i < rows.size(); i++) {
            consumer.accept(i, rows.get(i));
        }
    }
}
