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

        // 100% Tomcat Mode:
        // We assume the user has been authenticated by the Tomcat Realm (e.g.
        // MemoryRealm).
        // We construct the User object directly from the login.
        // Convention: The login IS the email.

        // Default role: STUDENT (The actual authorization is handled by web.xml
        // constraints,
        // this object is just for the application logic/session).
        User.Role role = User.Role.STUDENT;

        // In a real no-DB app, we might check if login is "admin" to set role ADMIN,
        // but here we focus on the student use case.
        if ("admin".equalsIgnoreCase(login)) {
            role = User.Role.ADMIN;
        }

        User user = new User(
                login,
                "", // Password not needed here, handled by Tomcat
                login, // Use login as placeholder for First Name
                role);

        user.setEmail(login); // Crucial: login is mapped to email for CSV lookup

        return Optional.of(user);
    }
}
