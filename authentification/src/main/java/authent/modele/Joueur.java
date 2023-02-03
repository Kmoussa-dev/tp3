package authent.modele;

public class Joueur {
    private String email;
    private String pseudo;
    private String motDePasse;


    public Joueur(String email, String pseudo, String motDePasse) {
        this.email = email;
        this.pseudo = pseudo;
        this.motDePasse = motDePasse;

    }


    public String getMotDePasse() {
        return motDePasse;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getEmail() {
        return email;
    }

    public boolean checkPassword(String password) {
        return this.motDePasse.equals(password);
    }
}
