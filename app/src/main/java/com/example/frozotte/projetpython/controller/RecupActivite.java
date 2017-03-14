package com.example.frozotte.projetpython.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.frozotte.projetpython.R;
import com.example.frozotte.projetpython.métier.Activite;
import com.example.frozotte.projetpython.métier.Ville;

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
import java.util.HashMap;

public class RecupActivite extends AsyncTask<String,Void,String>{

    StringBuffer buffer = null;
    String url = "";
    Activity context;
    ArrayList<String> listPays = new ArrayList<>();

        /*static HashMap<String, Integer> paysDrapeau = new HashMap<>();
        static{paysDrapeau.put("France", R.drawable.drapeau_france);
            paysDrapeau.put("Angleterre", R.drawable.drapeau_angleterre);
            paysDrapeau.put("Australie", R.drawable.drapeau_australie);}*/


    public RecupActivite(Activity context, String url){
        this.context = context;
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
            JSONArray parentArray = parentObject.getJSONArray("activite");

            StringBuffer finalBufferedDate = new StringBuffer();
            for (int i=0; i<parentArray.length();i++){
                JSONObject finalObject = parentArray.getJSONObject(i);

                String activiteName = finalObject.getString("nom");
                String description = finalObject.getString("description");
                finalBufferedDate.append(activiteName + " - " + description + "\n");
            }

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

    @Override
    protected void onPostExecute(String result){

    }
}
