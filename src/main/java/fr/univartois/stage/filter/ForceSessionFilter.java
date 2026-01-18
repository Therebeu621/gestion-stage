package fr.univartois.stage.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ForceSessionFilter implements Filter {

    @jakarta.inject.Inject
    private fr.univartois.stage.model.UserSession userSession;

    @jakarta.inject.Inject
    private fr.univartois.stage.repository.UserRepository userRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            req.getSession(true); // Force création session

            java.security.Principal principal = req.getUserPrincipal();
            if (principal != null) {
                // Si authentifié par conteneur mais pas encore dans UserSession
                if (!userSession.isLoggedIn() || !principal.getName().equals(userSession.getUser().getLogin())) {
                    userRepository.findByLogin(principal.getName()).ifPresent(u -> {
                        // Mise à jour dynamique du rôle selon sécurité conteneur
                        if (req.isUserInRole("ADMIN")) {
                            u.setRole(fr.univartois.stage.model.User.Role.ADMIN);
                        } else if (req.isUserInRole("STUDENT")) {
                            u.setRole(fr.univartois.stage.model.User.Role.STUDENT);
                        }
                        userSession.setUser(u);
                    });
                }
            }
        }

        chain.doFilter(request, response);
    }
}
