package com.example.frozotte.projetpython.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.frozotte.projetpython.Ville;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

/**
 * Created by frozotte on 10/03/2017.
 */

public class ConnectionServer extends AsyncTask<String,Void,String>{

    StringBuffer buffer = null;
    String url = "";

    public ConnectionServer(String url){
        this.url = url;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(this.url);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null){
                Log.i("result", line);
                buffer.append(line);
            }

            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            JSONArray parentArray = parentObject.getJSONArray("nom");

            StringBuffer finalBufferedData = new StringBuffer();
            for (int i=0; i<parentArray.length();i++) {
                JSONObject finalObject = parentArray.getJSONObject(i);
                Ville ville = new Ville();
                ville.setNom(finalObject.getString("nom"));
                ville.setPays(finalObject.getString("pays"));
                //finalBufferedData.append(paysName + " - " + villeName + "\n");
            }
            return finalBufferedData.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            connection.disconnect();
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }
}
