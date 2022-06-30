package com.example.innovativebanking;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DownloadAsync extends AsyncTask<String, Void, String> {

    private static final String TAG = DownloadAsync.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        Log.d(TAG, "Input string: " + strings[0] + "Salut");
        HttpURLConnection httpURLConnection = null;
        try {
            // Step 1: Initialize an URL object
            URL url = new URL(strings[0]);
            // Step 2: Create an url connection
            URLConnection connection = url.openConnection();
            if (connection instanceof HttpURLConnection) {
                // Step 3: Sending parameters
                httpURLConnection = (HttpURLConnection) connection;
                httpURLConnection.connect();
                int resultCode = httpURLConnection.getResponseCode();
                // Step 4:  Testing the response code
                if (resultCode == httpURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream(); //response stream
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName(StandardCharsets.UTF_8.name()));
                    StringBuilder stringBuilder = new StringBuilder();
                    // Step 5: Reading the response stream
                    try (Reader reader = new BufferedReader(inputStreamReader)) {
                        int ch = 0;
                        while ((ch = reader.read()) != -1) {
                            stringBuilder.append((char) ch);
                        }
                    }
                    result = stringBuilder.toString();
                    Log.d(TAG, "Content + " + stringBuilder.toString());
                } else {
                    throw new Exception("Invalid HTTP Connection");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return result;
    }

}
