package fr.tonychouteau.weatherwidget.remote.http;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;

public class AsynchJsonHandler extends AsyncTask<URL, Void, JSONObject> {

    private Consumer<JSONObject> consumer;

    public void setConsumer(Consumer<JSONObject> consumer) {
        this.consumer = consumer;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(JSONObject result) {
        consumer.accept(result);
    }

    @Override
    protected JSONObject doInBackground(URL ...urls) {
        return get_json(urls[0]);
    }


    private JSONObject get_json(URL url) {
        try {
            HttpURLConnection connection  = (HttpURLConnection) url.openConnection();

            connection.connect();
            int resCode = connection.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();

                return new JSONObject(sb.toString());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
