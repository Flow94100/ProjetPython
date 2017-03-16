package com.example.frozotte.projetpython.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.transition.SidePropagation;
import android.util.Log;
import android.widget.Toast;

import com.example.frozotte.projetpython.AccueilActivity;
import com.example.frozotte.projetpython.security.PostData;

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
 * Created by Abraham_PC on 16/03/2017.
 */

public class CommentController extends AsyncTask<String, Void, String> {


    private Activity context;

    public CommentController(Activity activity){
        context = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        String urlstring = params[0];
        String commentaire = params[1];
        String note = params[2] ;
        String id_activite = params[3];
        String id_user = params[4];

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        StringBuffer buffer = null;
        try {
            HashMap<String, String> postData = new HashMap<>();
            postData.put("id_user", id_user);
            postData.put("id_activite", id_activite);
            postData.put("note", note);
            postData.put("cmt", commentaire);

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
            jsonObject = new JSONObject(result);
            String error = jsonObject.getString("error");
            if(error.trim().equals("no_error")){
                Toast.makeText(context, "Votre note et commentaire ont bien été pris en compte", Toast.LENGTH_LONG).show();
                Intent iAccueil = new Intent(context, AccueilActivity.class);
                //iAccueil.putExtra("user", user);
                context.startActivity(iAccueil);
            }else
                Toast.makeText(context, "Vous avez déjà commenté ce post", Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();

        }
    }
}
