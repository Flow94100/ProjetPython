package com.example.frozotte.projetpython;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frozotte.projetpython.controller.InsriptionController;

public class InscriptionActivity extends AppCompatActivity {

    TextView txtLog;
    TextView txtMail;
    TextInputLayout txtMdp;
    Button btnInscri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        txtLog = (TextView)findViewById(R.id.txtLog);
        txtMail = (TextView)findViewById(R.id.txtMail);
        txtMdp = (TextInputLayout)findViewById(R.id.mdpInscri);
        btnInscri = (Button)findViewById(R.id.btnInscri);

        btnInscri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtLog.getText().toString().equals("") || txtMail.getText().toString().equals("") || txtMdp.getEditText().getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Vueillez remplir tout les champs", Toast.LENGTH_LONG).show();
                }else{

                    String user = txtLog.getText().toString();
                    String mail = txtMail.getText().toString();
                    String pwd = txtMdp.getEditText().getText().toString();
                    String url = "http://192.168.137.116/contents/api/inscription";

                    InsriptionController insriptionController = new InsriptionController();
                    insriptionController.execute(url,user,pwd,mail);

                    Intent iAccueil = new Intent(getApplicationContext(), AccueilActivity.class);
                    iAccueil.putExtra("user", user);
                    startActivity(iAccueil);
                }
            }
        });

    }
}
