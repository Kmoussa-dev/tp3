package pileouface.modele;

import java.util.ArrayList;
import java.util.Collection;

public class Joueur {

    private String pseudo;
    private Collection<Partie> historique;
    private int nbPartiesJouees;
    private int nbPartiesGagnees;


    public Joueur(String pseudo) {
        this.pseudo = pseudo;
        this.historique = new ArrayList<>();
        this.nbPartiesJouees = 0;
        this.nbPartiesGagnees = 0;
    }



    public Partie jouer(String prediction) {
        String tirage = (int)(Math.random()*100)%2 == 0?"Pile":"Face";

        this.nbPartiesJouees++;
        Partie partie = new Partie(prediction, tirage);
        if (partie.getVerdict()) {
            this.nbPartiesGagnees++;
        }
        this.historique.add(partie);
        return partie;
    }





    public String getPseudo() {
        return pseudo;
    }

    public Collection<Partie> getHistorique() {
        return historique;
    }

    public int getNbPartiesJouees() {
        return nbPartiesJouees;
    }

    public int getNbPartiesGagnees() {
        return nbPartiesGagnees;
    }


}
