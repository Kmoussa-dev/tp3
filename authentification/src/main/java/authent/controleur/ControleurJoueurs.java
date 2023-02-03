package authent.controleur;

import authent.facade.FacadeJoueur;
import authent.facade.erreurs.JoueurInexistantException;
import authent.facade.erreurs.PseudoDejaPrisException;
import authent.modele.Joueur;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.function.Function;


@RestController
@RequestMapping("/authent")
@EnableWebSecurity
public class ControleurJoueurs {
    private static final Object TOKEN_PREFIX = "Bearer ";
    @Autowired
    FacadeJoueur facadeJoueurs;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    Function<Joueur,String> genereToken;

    record AuthDTO(@Size(min=3,max=128) String login, @Size(min=4,max=128) String password) {};
    record JoueurDTO(@Email String email, @NotNull @Size(min=3,max=128) String pseudo, @Size(min=4,max=128) String password) {};


    @PostMapping(value = "/inscription")
    public ResponseEntity<String> inscription(@Valid @RequestBody JoueurDTO joueurDTO) {
        try {
            String encodedPassword = passwordEncoder.encode(joueurDTO.password());
            Joueur j = facadeJoueurs.inscription(joueurDTO.email(), joueurDTO.pseudo(), encodedPassword);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{pseudo}")
                    .buildAndExpand(joueurDTO.pseudo()).toUri();
            return ResponseEntity.created(location).header(HttpHeaders.AUTHORIZATION, TOKEN_PREFIX+genereToken.apply(j)).build();
        } catch (PseudoDejaPrisException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Pseudo "+joueurDTO.pseudo()+" déjà pris");
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthDTO joueurDTO) {
        Joueur j = null;
        try {
            j = facadeJoueurs.getJoueurByPseudo(joueurDTO.login());
        } catch (JoueurInexistantException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (passwordEncoder.matches(joueurDTO.password(), j.getMotDePasse())) {
            String token = genereToken.apply(j);
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION,"Bearer "+token).build();
        };
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(value = "/inscription/{pseudo}")
    @PreAuthorize("#pseudo == authentication.name")
    public ResponseEntity<Joueur> get(@PathVariable String pseudo) {
        Joueur j = null;
        try {
            j = facadeJoueurs.getJoueurByPseudo(pseudo);
        } catch (JoueurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(j);
    }

    @DeleteMapping(value = "/inscription/{pseudo}")
    @PreAuthorize("#pseudo == authentication.name")
    public ResponseEntity<String> desinscription(@PathVariable String pseudo) {
        try {
            this.facadeJoueurs.desincription(pseudo);
        } catch (JoueurInexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mauvaises informations transmises !");
        }
        return ResponseEntity.noContent().build();
    }

}
