package com.example.frozotte.projetpython;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button btnConnexion;
    TextView txtSkip;
    TextView txtInscri;
    EditText editLog;
    EditText editMdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtSkip = (TextView)findViewById(R.id.txtSkip);
        txtInscri = (TextView)findViewById(R.id.txtInscri);
        btnConnexion = (Button)findViewById(R.id.btnConn);
        editLog = (EditText)findViewById(R.id.editText);
        editMdp = (EditText)findViewById(R.id.editText2);

        //Si pas inscri, passer au fil d'actu directement
        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iActu = new Intent(getApplicationContext(),AccueilActivity.class);
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
                if (editLog.getText().toString().equals("admin") && editMdp.getText().toString().equals("admin")){
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
