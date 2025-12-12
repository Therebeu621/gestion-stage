package fr.univartois.stage.service;

import fr.univartois.stage.model.User;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class LdapService {

    // Simulation de l'annuaire LDAP
    public Optional<User> authenticate(String login, String password) {
        // En "vrai", ici on se connecterait au serveur LDAP avec unboundid-ldapsdk
        // Pour la démo : on accepte un étudiant de test

        if ("etudiant".equals(login) && "password".equals(password)) {
            // On mappe l'étudiant avec son adresse universitaire (clé de jointure pour le
            // CSV)
            User student = new User(login, "password", "Étudiant Test", User.Role.STUDENT);
            student.setEmail("mail1@ens.univ-artois.fr"); // Correspond à la 1ère ligne du CSV
            return Optional.of(student);
        }

        return Optional.empty();
    }
}
