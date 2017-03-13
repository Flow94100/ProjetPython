package com.example.frozotte.projetpython;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frozotte.projetpython.controller.ConnectionServer;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {

    Button btnConnexion;
    TextView txtSkip;
    TextView txtInscri;
    EditText editLog;
    CheckBox checkLogin;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*SharedPreferences recupLoginMdp = getSharedPreferences("login", MODE_PRIVATE);
        String recupLogin = recupLoginMdp.getString("login", null);
        String recupMdp = recupLoginMdp.getString("mdp", null);

        if(recupLogin != null && recupMdp !=null){
            authentification = new JsonAuthentification(Login.this);
            authentification.execute(recupLogin, recupMdp);
            try {
                maListe = authentification.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            Intent i = new Intent(Login.this, ListeLivraison.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST_LIVRAISON", (Serializable) maListe);
            i.putExtra("BUNDLE_LIVRAISON", args);
            startActivity(i);
        }*/

        setContentView(R.layout.activity_login);

        txtSkip = (TextView)findViewById(R.id.txtSkip);
        txtInscri = (TextView)findViewById(R.id.txtInscri);
        btnConnexion = (Button)findViewById(R.id.btnConn);
        editLog = (EditText)findViewById(R.id.editText);
        checkLogin = (CheckBox)findViewById(R.id.checkBox2);

        //Si pas inscri, passer au fil d'actu directement
        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionServer connection = new ConnectionServer("http://192.168.43.148:8000/contents/api/villes/?format=json");
                connection.execute();
                try {
                    result = connection.get();
                    Log.i("result", result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent iActu = new Intent(getApplicationContext(),AccueilActivity.class);
                iActu.putExtra("result", result);
                startActivity(iActu);
                finish();
            }
        });

        //Si pas de compte, renvoie le formulaire d'inscription
        txtInscri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iInscri = new Intent(getApplication(),InscriptionActivity.class);
                startActivity(iInscri);
            }
        });

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editLog.getText().toString().equals("admin")){
                    Intent iActu = new Intent(getApplicationContext(),AccueilActivity.class);
                    startActivity(iActu);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Utilisateur inconnu", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
