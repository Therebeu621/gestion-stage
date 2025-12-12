package fr.univartois.stage.service;

import fr.univartois.stage.model.User;
import fr.univartois.stage.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Optional;

@ApplicationScoped
public class AuthService {

    @Inject
    private UserRepository userRepository;

    public Optional<User> authenticate(String login, String password) {
        return userRepository.findByLogin(login)
                .filter(u -> u.getPassword().equals(password));
    }
}
