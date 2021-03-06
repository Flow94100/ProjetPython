package com.example.frozotte.projetpython.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frozotte.projetpython.CommentaireActivity;
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

public class RecupActivite extends AsyncTask<String,Void,String>{

    StringBuffer buffer = null;
    String url = "";
    Activity context;
    ArrayList<String> listPays = new ArrayList<>();
    ArrayList<Activite> listActivite = new ArrayList<>();

        static HashMap<String, Integer> imgAct = new HashMap<>();
        static{imgAct.put("Tour Eiffel", R.drawable.eiffel_tower);
            imgAct.put("Musée du Louvre", R.drawable.louvre);
            imgAct.put("Big Ben", R.drawable.big_ben);
            imgAct.put("London Eye", R.drawable.london_eyes);
            imgAct.put("Opéra House", R.drawable.opera);
            imgAct.put("Harbour Bridge", R.drawable.harbour_bridge);
            imgAct.put("Arc de Triomphe", R.drawable.arc_de_triomphe);
            imgAct.put("Montmartre", R.drawable.montmartre);}


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
    protected void onPostExecute(String result){

        JSONObject parentObject = null;
        try {
            final JSONArray parentArray = new JSONArray(result);
            final ListView listView = (ListView)context.findViewById(R.id.listActu);
            ArrayList<Activite> items = new ArrayList<Activite>();

            StringBuffer finalBufferedDate = new StringBuffer();
            for (int i=0; i<parentArray.length();i++){
                JSONObject finalObject = parentArray.getJSONObject(i);

                String activiteName = finalObject.getString("nom");
                String description = finalObject.getString("description");
                finalBufferedDate.append(activiteName + " - " + description + "\n");

                try {
                    final JSONArray activiteArray = new JSONArray(result);
                    final Activite activite = new Activite();
                    activite.setId(finalObject.getInt("id_activite"));
                    activite.setNom(finalObject.getString("nom"));
                    activite.setDescription(finalObject.getString("description"));
                    listActivite.add(activite);

                    CustomAdapter adapter = new CustomAdapter(listActivite);
                    listView.setAdapter(adapter);
                    //clic
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Activite activite = listActivite.get(position);

                            Intent intent = new Intent(context, CommentaireActivity.class);
                            intent.putExtra("activite", activite.getNom());
                            context.startActivity(intent);

                        }
                    });

                    // long clic
                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            SharedPreferences sharedPref = context.getSharedPreferences("my_preference", Context.MODE_PRIVATE);
                            final int id_user = sharedPref.getInt("id_user", 0);
                            if(id_user == 0){
                                Toast.makeText( context, "Vous devez être connecté pour commenter", Toast.LENGTH_SHORT).show();
                                return false;
                            }
                            final int pos = position;
                            Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.dialog_cmt_note);
                            final EditText editCmt = (EditText)dialog.findViewById(R.id.editTxtComentaire);
                            final RatingBar ratingBarNote = (RatingBar) dialog.findViewById(R.id.ratingBarNote);
                            dialog.findViewById(R.id.btnValideCmt).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Activite activite = listActivite.get(pos);

                                    CommentController commentController = new CommentController(context);
                                    commentController.execute("http://"+ LoginActivity.ip+"/contents/api/cmt_note/",
                                            editCmt.getText().toString(), Float.toString(ratingBarNote.getRating()),
                                            String.valueOf(activite.getId()), String.valueOf(id_user));
                                }
                            });
                            dialog.show();

                            return false;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Classe custom adapter
    public class CustomAdapter extends BaseAdapter{

        ArrayList<Activite> listActivite = new ArrayList<Activite>();

        public CustomAdapter(ArrayList<Activite> listActivite){
            this.listActivite = listActivite;
        }

        @Override
        public int getCount() {
            return listActivite.size();
        }

        @Override
        public Object getItem(int position) {
            return listActivite.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                view = layoutInflater.inflate(R.layout.list_actu_item, null);
            }

            Activite activite = (Activite) getItem(position);

            if (activite != null) {
                ImageView imgActivite = (ImageView) view.findViewById(R.id.imgActu);
                TextView txtNom = (TextView) view.findViewById(R.id.txtActuNom);
                TextView txtDescr = (TextView) view.findViewById(R.id.txtActuDescr);

                txtNom.setText(listActivite.get(position).getNom());
                txtDescr.setText(listActivite.get(position).getDescription());
                try {
                    imgActivite.setImageResource(RecupActivite.imgAct.get(activite.getNom()));
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            return view;
        }
    }

}
