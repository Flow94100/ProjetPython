package com.example.frozotte.projetpython;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InscriptionActivity extends AppCompatActivity {

    TextView txtLog;
    TextView txtMail;
    TextView txtMdp;
    Button btnInscri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        txtLog = (TextView)findViewById(R.id.txtLog);
        txtMail = (TextView)findViewById(R.id.txtMail);
        //txtMdp = (TextView)findViewById(R.id.mdpInscri);
        btnInscri = (Button)findViewById(R.id.btnInscri);

        btnInscri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtLog.getText().toString().equals("") || txtMail.getText().toString().equals("") || txtMdp.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Vueillez remplir tout les champs", Toast.LENGTH_LONG).show();
                }else{
                    Intent iAccueil = new Intent(getApplicationContext(), AccueilActivity.class);
                    startActivity(iAccueil);
                }
            }
        });

    }
}
