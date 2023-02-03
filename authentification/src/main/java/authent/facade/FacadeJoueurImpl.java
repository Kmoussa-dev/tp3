package authent.facade;

import authent.facade.erreurs.JoueurInexistantException;
import authent.facade.erreurs.PseudoDejaPrisException;
import org.springframework.stereotype.Component;
import authent.modele.*;
import java.util.HashMap;
import java.util.Map;


@Component("facadeJoueurs")
public class FacadeJoueurImpl implements FacadeJoueur {


    /**
     * Dictionnaire des joueurs inscrits
     */
    private Map<String,Joueur> joueurs;


    public FacadeJoueurImpl() {
        this.joueurs = new HashMap<>();
    }


    @Override
    public Joueur inscription(String email, String nouveauJoueur, String mdp) throws PseudoDejaPrisException {
        if (joueurs.containsKey(nouveauJoueur))
            throw new PseudoDejaPrisException();
        Joueur v = new Joueur(email, nouveauJoueur, mdp);
        this.joueurs.put(nouveauJoueur, v);
        return v;
    }

    @Override
    public Joueur getJoueurByPseudo(String idJoueur)  throws JoueurInexistantException {
        if (!joueurs.containsKey(idJoueur)) {
            throw new JoueurInexistantException();
        }
        return joueurs.get(idJoueur);
    }

    @Override
    public void desincription(String pseudo) throws JoueurInexistantException {
        if (!joueurs.containsKey(pseudo))
            throw new JoueurInexistantException();
        joueurs.remove(pseudo);
    }


}
