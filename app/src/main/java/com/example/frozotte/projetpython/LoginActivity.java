package com.example.frozotte.projetpython;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    Button btnConnexion;
    TextView txtSkip;
    TextView txtInscri;
    EditText editLog;
    CheckBox checkLogin;
    String result;
    String login;

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
        editLog = (EditText)findViewById(R.id.editLog);
        checkLogin = (CheckBox)findViewById(R.id.checkBox2);

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
                if (editLog.getText().toString().equals("admin")){
                    login = editLog.getText().toString();
                    Intent iActu = new Intent(getApplicationContext(),AccueilActivity.class);
                    iActu.putExtra("login", login);
                    startActivity(iActu);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Utilisateur inconnu", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
