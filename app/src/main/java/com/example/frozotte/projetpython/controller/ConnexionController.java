package com.example.frozotte.projetpython.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.frozotte.projetpython.AccueilActivity;
import com.example.frozotte.projetpython.R;
import com.example.frozotte.projetpython.métier.Ville;
import com.example.frozotte.projetpython.security.PostData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Ismael on 14/03/2017.
 */

public class ConnexionController extends AsyncTask<String, Void, String>{

    Activity activite;

    public ConnexionController(Activity activite){
        this.activite = activite;
    }

    @Override
    protected String doInBackground(String... params) {

        String urlstring = params[0];
        String user = params[1];
        String pwd = params[2];

        //urlstring += "/" +user+ "/" +pwd;

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        StringBuffer buffer = null;
        try {
            HashMap<String, String> postData = new HashMap<>();
            postData.put("user", user);
            postData.put("pwd", pwd);

            URL url = new URL(urlstring);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(PostData.getPostDataString(postData));

            connection.connect();
            writer.flush();
            writer.close();
            os.close();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
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
        JSONObject jsonObject = null;
        try {
            //parentObject = new JSONObject(result);
            jsonObject = new JSONObject(result);

            String error = jsonObject.getString("error");

            Toast.makeText(activite, "Utilisateur inconnu", Toast.LENGTH_LONG).show();
            Log.i("erreur", error);


        } catch (JSONException e) {

            String login = null;
            try {
                login = jsonObject.getString("login");
                SharedPreferences sharedPrefs = this.activite.getSharedPreferences("my_preference", Context.MODE_PRIVATE);
                sharedPrefs.edit().putInt("id_user", jsonObject.getInt("id_user")).commit();

            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Intent iActu = new Intent(activite,AccueilActivity.class);
            iActu.putExtra("login", login);

            Toast.makeText(activite,"Connexion réussi", Toast.LENGTH_LONG).show();
            activite.startActivity(iActu);
            activite.finish();
            e.printStackTrace();
        }
    }
}