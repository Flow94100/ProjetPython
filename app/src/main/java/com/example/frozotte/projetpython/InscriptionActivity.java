package com.example.frozotte.projetpython;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        txtMdp = (TextView)findViewById(R.id.txtMdp);
        btnInscri = (Button)findViewById(R.id.btnInscri);

        btnInscri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtLog.getText().toString().equals("")){
                    txtLog.setError("Champ obligatoire !");
                }
                if (txtMail.getText().toString().equals("")){
                    txtMail.setError("Champ obligatoire !");
                }
                if (txtMdp.getText().toString().equals("")){
                    txtMdp.setError("Champ obligatoire !");
                }
            }
        });

    }
}
