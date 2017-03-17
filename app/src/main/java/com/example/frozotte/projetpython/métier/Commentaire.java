package com.example.frozotte.projetpython.métier;

/**
 * Created by Abraham_PC on 17/03/2017.
 */

public class Commentaire {

    private String comentaire;
    private float note;

    public Commentaire(){

    }

    public String getComentaire() {
        return comentaire;
    }

    public void setComentaire(String comentaire) {
        this.comentaire = comentaire;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return comentaire+" ("+note+"/"+"5)";
    }
}
