package fr.univartois.stage.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Filter to force session creation.
 * This ensures the session cookie is set before the response is committed,
 * avoiding "Cannot create a session after the response has been committed"
 * errors
 * caused by late running JAX-RS filters (like Krazo CSRF).
 */
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
            req.getSession(true); // Force session creation

            java.security.Principal principal = req.getUserPrincipal();
            if (principal != null) {
                // If user is authenticated by container but not yet in UserSession
                if (!userSession.isLoggedIn() || !principal.getName().equals(userSession.getUser().getLogin())) {
                    userRepository.findByLogin(principal.getName()).ifPresent(u -> {
                        // Dynamically update role based on container security
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
