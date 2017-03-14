package com.example.frozotte.projetpython.controller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ismael on 13/03/2017.
 */

public class InsriptionController extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {

        String urlstring = params[0];
        String user = params[1];
        String pwd = params[2];
        String mail = params[3];
        String statut = "user";

        urlstring+= "/"+user+"/"+pwd+"/"+mail+"/"+statut;

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        StringBuffer buffer = null;
        try {
            URL url = new URL(urlstring);
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
