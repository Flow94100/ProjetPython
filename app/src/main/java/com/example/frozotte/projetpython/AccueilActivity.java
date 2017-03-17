package com.example.frozotte.projetpython;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frozotte.projetpython.controller.RecupActivite;
import com.example.frozotte.projetpython.controller.RecupVille;

import java.util.concurrent.ExecutionException;

public class AccueilActivity extends AppCompatActivity {

    ImageView imgDeconn;
    TextView txtLogin;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        RecupVille recupVille = new RecupVille(AccueilActivity.this,"http://"+LoginActivity.ip+"/contents/api/villes/?format=json");
        recupVille.execute();

        RecupActivite recupActivite= new RecupActivite(AccueilActivity.this, "http://"+LoginActivity.ip+"/contents/api/activites/?format=json");
        recupActivite.execute();

        Intent intent= getIntent();
        String result = intent.getStringExtra("result");
        String login = intent.getStringExtra("login");

        txtLogin = (TextView)findViewById(R.id.txtTitreLog);
        imgDeconn = (ImageView)findViewById(R.id.imgDeconn);

        txtLogin.setText(login);

        imgDeconn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPrefs = getSharedPreferences("my_preference", Context.MODE_PRIVATE);
                sharedPrefs.edit().putInt("id_user", 0).commit();
                Intent iDeconn = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(iDeconn);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ad = new AlertDialog.Builder(AccueilActivity.this);
        ad.setMessage("Voulez-vous vraiment quitter ?").setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = ad.create();
        alert.setTitle("Quitter");
        alert.show();
    }
}
