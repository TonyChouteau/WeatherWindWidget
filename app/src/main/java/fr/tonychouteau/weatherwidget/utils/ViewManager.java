package fr.tonychouteau.weatherwidget.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;

public class ViewManager {

    private AppWidgetTarget appWidgetTarget;
    private ContextManager contextManager;

    public ViewManager(ContextManager contextManager) {
        this.contextManager = contextManager;
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
}
