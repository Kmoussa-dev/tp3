package authent.config;

import authent.facade.FacadeJoueur;
import authent.facade.erreurs.JoueurInexistantException;
import authent.modele.Joueur;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;


public class CustomUserDetailsService implements UserDetailsService {
    private static final String[] ROLES_ADMIN = {"USER","ADMIN"};
    private static final String[] NO_ROLE={};

    private PasswordEncoder passwordEncoder;
    private FacadeJoueur facadeJoueurs;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder, FacadeJoueur facadeJoueur) {
        this.passwordEncoder = passwordEncoder;
        this.facadeJoueurs = facadeJoueur;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Joueur utilisateur = null;
        try {
            utilisateur = facadeJoueurs.getJoueurByPseudo(s);
        } catch (JoueurInexistantException e) {
            throw new UsernameNotFoundException("User "+s+" not found");
        }
        String[] roles = NO_ROLE;
        UserDetails userDetails = User.builder()
                .username(utilisateur.getPseudo())
                .password(passwordEncoder.encode(utilisateur.getMotDePasse()))
                .roles(roles)
                .build();

        return userDetails;
    }
}
