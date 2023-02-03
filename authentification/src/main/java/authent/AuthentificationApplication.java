package authent;

import authent.facade.FacadeJoueur;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthentificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthentificationApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(FacadeJoueur facadeJoueur, PasswordEncoder passwordEncoder) {
        return args -> facadeJoueur.inscription("yohan.boichut@gmail.com","yohan", passwordEncoder.encode("pass"));
    }
}
