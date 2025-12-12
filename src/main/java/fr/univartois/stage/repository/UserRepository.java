package fr.univartois.stage.repository;

import fr.univartois.stage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    private final Map<String, User> users = new HashMap<>();

    public UserRepository() {
        // Default users for testing
        users.put("johan", new User("johan", "password", "Johan", User.Role.RESPONSIBLE));
        users.put("admin", new User("admin", "admin", "Admin", User.Role.ADMIN));
    }

    public Optional<User> findByLogin(String login) {
        return Optional.ofNullable(users.get(login));
    }
}
