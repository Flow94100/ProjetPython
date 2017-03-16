package com.example.frozotte.projetpython.controller;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frozotte.projetpython.LoginActivity;
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
import java.util.concurrent.ExecutionException;

/**
 * Created by frozotte on 10/03/2017.
 */

public class RecupVille extends AsyncTask<String,Void,String>{

    StringBuffer buffer = null;
    String url = "";
    Activity context;
    ArrayList<String> listPays = new ArrayList<>();
    AlertDialog dialog;

        static HashMap<String, Integer>paysDrapeau = new HashMap<>();
        static{paysDrapeau.put("France", R.drawable.drapeau_france);
        paysDrapeau.put("Angleterre", R.drawable.drapeau_angleterre);
        paysDrapeau.put("Australie", R.drawable.drapeau_australie);}


    public RecupVille(Activity context, String url){
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
    protected void onPostExecute(final String result){

        final JSONObject parentObject = null;
        LinearLayout layoutPays = (LinearLayout)context.findViewById(R.id.layoutFlag);

        try {
            //parentObject = new JSONObject(result);
            final JSONArray parentArray = new JSONArray(result);

            StringBuffer finalBufferedData = new StringBuffer();
            for (int i=0; i<parentArray.length();i++) {
                final JSONObject finalObject = parentArray.getJSONObject(i);
                final Ville ville = new Ville();
                ville.setNom(finalObject.getString("nom"));
                ville.setPays(finalObject.getString("pays"));
                if (listPays.contains(ville.getPays())){
                    continue;
                }else{
                    listPays.add(ville.getPays());
                }
                final ImageView imgPays = new ImageView(context);
                imgPays.setImageResource(RecupVille.paysDrapeau.get(ville.getPays()));
                final TextView txtPays = new TextView(context);
                layoutPays.addView(imgPays, 0);
                imgPays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ListView listView = new ListView(context);
                        ArrayList<String> items = new ArrayList<String>();

                        for (int j=0; j<parentArray.length();j++){
                            try {
                                if (ville.getPays().equals(parentArray.getJSONObject(j).getString("pays"))){
                                    items.add(parentArray.getJSONObject(j).getString("nom"));
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            RecupActivite recupActivite = new RecupActivite(context, "http://"+ LoginActivity.ip+"/contents/api/activitebyville/" + listView.getItemAtPosition(position).toString());
                                            recupActivite.execute();
                                            dialog.dismiss();
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.list_ville_item, R.id.txtItem, items);
                        listView.setAdapter(adapter);
                        showDialogListView(listView);
                    }
                });
                //finalBufferedData.append(paysName + " - " + villeName + "\n");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showDialogListView(ListView listView){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setView(listView);
        dialog = builder.create();
        dialog.show();
    }
}
