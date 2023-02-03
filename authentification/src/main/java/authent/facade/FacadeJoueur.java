package authent.facade;

import authent.facade.erreurs.JoueurInexistantException;
import authent.facade.erreurs.PseudoDejaPrisException;
import authent.modele.*;

public interface FacadeJoueur {
    /**
     * Inscription d'un nouveau joueur à la POFOL
     *
     * @param nouveauJoueur
     * @param mdp
     * @return
     * @throws PseudoDejaPrisException
     */
    Joueur inscription(String email, String nouveauJoueur, String mdp) throws PseudoDejaPrisException;

    Joueur getJoueurByPseudo(String idJoueur) throws JoueurInexistantException;


    /**
     * Permet de se désinscrire de la plate-forme
     *
     * @param pseudo
     * @throws JoueurInexistantException
     */
    void desincription(String pseudo) throws JoueurInexistantException;


}
