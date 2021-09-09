package fr.tonychouteau.weatherwidget.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

public class AsynchImageHandler extends AsyncTask<String, Void, Bitmap> {

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
    protected Bitmap doInBackground(String... urls) {
        return download_Image(urls[0]);
    }


    private Bitmap download_Image(String image_url) {
        try {
            Log.d("Test", "1");
            URL url = new URL(image_url);
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();

            connection.connect();
            int resCode = connection.getResponseCode();
            Log.d("Test", "2");

            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                Bitmap img = BitmapFactory.decodeStream(is);

                connection.disconnect();

                Log.d("Test", "3");
                return img;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
