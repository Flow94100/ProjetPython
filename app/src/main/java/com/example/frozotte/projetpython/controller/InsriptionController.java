package com.example.frozotte.projetpython.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
 * Created by Ismael on 13/03/2017.
 */

public class InsriptionController extends AsyncTask<String, Void, String> {

    private Activity context;

    public InsriptionController(Activity context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {

        String urlstring = params[0];
        String user = params[1];
        String pwd = params[2];
        String mail = params[3];
        String statut = "user";

        //urlstring+= "/"+user+"/"+pwd+"/"+mail+"/"+statut;

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        StringBuffer buffer = null;
        try {
            HashMap<String, String> postData = new HashMap<>();
            postData.put("user", user);
            postData.put("pwd", pwd);
            postData.put("mail", mail);
            postData.put("status", statut);

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
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(result);
            String error = jsonObject.getString("error");
            if(error.trim().equals("no_error")){
                Toast.makeText(context, "Vous êtes maintenant inscrit", Toast.LENGTH_LONG).show();
                Intent iAccueil = new Intent(context, AccueilActivity.class);
                //iAccueil.putExtra("user", user);
                context.startActivity(iAccueil);
            }else
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();

        }



    }
}
