package com.example.frozotte.projetpython.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorTreeAdapter;

import com.example.frozotte.projetpython.R;
import com.example.frozotte.projetpython.métier.Commentaire;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Abraham_PC on 17/03/2017.
 */

public class NoteMoyenne extends AsyncTask<String, Void, String> {

    private Activity activity;

    public NoteMoyenne(Activity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {

        String urlString = params[0];
        String activite = params[1];

        StringBuffer buffer = new StringBuffer();
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            urlString +="/"+activite;
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));;

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

    @Override
    protected void onPostExecute(String result) {

        ListView listView = (ListView)this.activity.findViewById(R.id.listCmt);

        try {
            final JSONArray parentArray = new JSONArray(result);
            ArrayList<Commentaire> cmtList = new ArrayList<>();
            for (int i=0; i<parentArray.length(); i++){
                JSONObject jsonObject = parentArray.getJSONObject(i);
                String comt = jsonObject.getString("commentaire");
                float note = jsonObject.getLong("note");
                Commentaire commentaire = new Commentaire();
                commentaire.setComentaire(comt);
                commentaire.setNote(note);
                cmtList.add(commentaire);

                ArrayAdapter<Commentaire> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, cmtList);
                listView.setAdapter(adapter);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
