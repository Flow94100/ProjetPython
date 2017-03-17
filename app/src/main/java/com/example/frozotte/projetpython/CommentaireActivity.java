package com.example.frozotte.projetpython;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.frozotte.projetpython.controller.NoteMoyenne;

public class CommentaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commentaire);

        Intent intent= getIntent();
        String activite = intent.getStringExtra("activite");

        NoteMoyenne noteMoyenne = new NoteMoyenne(this);
        noteMoyenne.execute("http://"+ LoginActivity.ip+"/contents/api/commentairebyactivite", activite);
    }
}
