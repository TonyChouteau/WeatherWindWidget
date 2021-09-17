package fr.tonychouteau.weatherwidget.remote.http;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.Map;
import java.util.HashMap;

public class UrlHelper {

    private final String baseUrl;
    private final ArrayList<String> path;
    private final Map<String, Integer> keyToPath;
    private final Map<String, String> params;
    private URL url;

    private Boolean hasChanged;

    //=================================
    // Constructor
    //=================================

    public UrlHelper(String baseUrl) {
        this.baseUrl = baseUrl;

        this.path = new ArrayList<>();
        this.keyToPath = new HashMap<>();

        this.params = new HashMap<>();

        this.hasChanged = true;
    }

    //=================================
    // Chainable Methods
    //=================================

    public UrlHelper param(String param, String value) {
        params.put(param, value);
        this.hasChanged = true;

        return this;
    }

    public UrlHelper param(String param, Double value) {
        params.put(param, Double.toString(value));
        this.hasChanged = true;

        return this;
    }

    public UrlHelper removeParam(String param) {
        params.remove(param);
        this.hasChanged = true;

        return this;
    }

    public UrlHelper add(String path) {
        this.path.add(path);
        this.hasChanged = true;

        return this;
    }

    public UrlHelper add(String key, String path) {
        this.path.add(path);
        this.keyToPath.put(key, this.path.size() - 1);
        this.hasChanged = true;

        return this;
    }

    public UrlHelper changePath(String key, String path) {
        this.path.set(this.keyToPath.get(key), path);
        this.hasChanged = true;

        return this;
    }

    public Map<String, String> getParams() {
        return this.params;
    }

    public UrlHelper setParams(Map<String, String> params) {
        params.clear();
        params.forEach((param, value) -> {
            this.params.put(param, value);
        });
        this.hasChanged = true;

        return this;
    }

    //=================================
    // Public Methods
    //=================================

    public URL make() {
        if (!this.hasChanged) return this.url;

        StringJoiner joiner = new StringJoiner("");

        joiner.add(this.baseUrl);

        this.path.forEach((value) -> joiner.add(value));
        if (this.params.size() > 0) {
           joiner.add("?");
            this.params.forEach((param, value) -> joiner.add(param + "=" + value + "&"));
        }

        String urlString = joiner.toString();
        if (this.params.size() > 0) urlString = urlString.substring(0, urlString.length() -1);

        try {
            this.url = new URL(urlString);
            return this.url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //=================================
    // Private Methods
    //=================================

}
