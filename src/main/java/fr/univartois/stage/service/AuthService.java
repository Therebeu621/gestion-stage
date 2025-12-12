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

    @Inject
    private LdapService ldapService;

    public Optional<User> authenticate(String login, String password) {
        // Tente d'abord en local (Responsable / Admin)
        Optional<User> localUser = userRepository.findByLogin(login)
                .filter(u -> u.getPassword().equals(password));

        if (localUser.isPresent()) {
            return localUser;
        }

        // Sinon tente LDAP (Étudiant)
        return ldapService.authenticate(login, password);
    }
}
