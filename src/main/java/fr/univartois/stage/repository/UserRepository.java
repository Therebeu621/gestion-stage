package fr.univartois.stage.repository;

import fr.univartois.stage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    // Removed DataSource injection

    public Optional<User> findByLogin(String login) {
        if (login == null || login.isBlank()) {
            return Optional.empty();
        }

        // Mode Tomcat 100%:
        // On suppose l'utilisateur authentifié par le Realm Tomcat (ex: MemoryRealm).
        // On construit l'objet User via le login.
        // Convention: Login = Email.

        // Rôle par défaut: STUDENT (Auth réelle gérée par web.xml,
        // cet objet ne sert qu'à la logique appli/session).
        User.Role role = User.Role.STUDENT;

        if ("admin".equalsIgnoreCase(login)) {
            role = User.Role.ADMIN;
        }

        User user = new User(
                login,
                "", // Mot de passe géré par Tomcat
                login, // Utilise login comme Prénom temporaire
                role);

        user.setEmail(login); // Crucial: login map vers email pour lookup CSV

        return Optional.of(user);
    }
}
