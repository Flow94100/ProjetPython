package com.example.frozotte.projetpython;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AccueilActivity extends AppCompatActivity {

    ImageView imgDeconn;
    TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Intent intent= getIntent();
        String result = intent.getStringExtra("result");
        String login = intent.getStringExtra("login");

        txtLogin = (TextView)findViewById(R.id.txtTitreLog);
        imgDeconn = (ImageView)findViewById(R.id.imgDeconn);

        txtLogin.setText(login);

        imgDeconn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
