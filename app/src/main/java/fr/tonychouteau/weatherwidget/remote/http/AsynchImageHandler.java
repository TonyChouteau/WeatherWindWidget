package fr.tonychouteau.weatherwidget.remote.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

public class AsynchImageHandler extends AsyncTask<URL, Void, Bitmap> {

    private Consumer<Bitmap> consumer;

    public void setConsumer(Consumer<Bitmap> consumer) {
        this.consumer = consumer;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(Bitmap result) {
        consumer.accept(result);
    }

    @Override
    protected Bitmap doInBackground(URL... urls) {
        return download_Image(urls[0]);
    }


    private Bitmap download_Image(URL url) {
        try {
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();

            connection.connect();
            int resCode = connection.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                Bitmap img = BitmapFactory.decodeStream(is);

                connection.disconnect();

                return img;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
