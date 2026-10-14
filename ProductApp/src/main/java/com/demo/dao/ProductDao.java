package com.demo.dao;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.demo.model.Product;

public class ProductDao {

    private static final String PROJECT_ID = env("FIREBASE_PROJECT_ID");
    private static final String API_KEY    = env("FIREBASE_API_KEY");

    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public List<Product> getAllProducts() throws IOException, InterruptedException {
        String url = "https://firestore.googleapis.com/v1/projects/" + PROJECT_ID
                + "/databases/(default)/documents/products?pageSize=300&key=" + API_KEY;

        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .GET()
                .build();

        HttpResponse<String> response = HTTP.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Firestore returned " + response.statusCode() + ": " + response.body());
        }

        List<Product> products = new ArrayList<>();
        JSONArray docs = new JSONObject(response.body()).optJSONArray("documents");
        if (docs == null) return products;   // empty collection

        for (int i = 0; i < docs.length(); i++) {
            JSONObject doc = docs.getJSONObject(i);
            String path = doc.getString("name");                  // .../documents/products/abc123
            String id = path.substring(path.lastIndexOf('/') + 1);

            JSONObject fields = doc.optJSONObject("fields");
            if (fields == null) continue;

            products.add(new Product(
                    id,
                    readString(fields, "name"),
                    readNumber(fields, "price"),
                    (int) readNumber(fields, "stock")));
        }
        return products;
    }

  
    private static String readString(JSONObject fields, String key) {
        JSONObject f = fields.optJSONObject(key);
        return (f == null) ? "" : f.optString("stringValue", "");
    }

    
    private static double readNumber(JSONObject fields, String key) {
        JSONObject f = fields.optJSONObject(key);
        if (f == null) return 0;
        if (f.has("doubleValue"))  return f.getDouble("doubleValue");
        if (f.has("integerValue")) return Double.parseDouble(f.getString("integerValue"));
        return 0;
    }

    private static String env(String key) {
        String v = System.getenv(key);
        if (v == null || v.isBlank()) {
            throw new IllegalStateException("Environment variable " + key + " is not set");
        }
        return v;
    }
}