package com.example.frozotte.projetpython.métier;

/**
 * Created by frozotte on 14/03/2017.
 */

public class Activite {

    private String nom;
    private String description;
    private String img;

    public Activite(){

    }

    public Activite(String nom, String descripion){
        this.nom = nom;
        this.description = descripion;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
