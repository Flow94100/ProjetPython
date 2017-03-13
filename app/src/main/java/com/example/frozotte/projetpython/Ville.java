package com.example.frozotte.projetpython;

/**
 * Created by frozotte on 13/03/2017.
 */

public class Ville {

    private String nom;
    private String pays;

    public Ville(){

    }

    public Ville(String nom, String pays){
        this.nom = nom;
        this.pays = pays;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }
}
