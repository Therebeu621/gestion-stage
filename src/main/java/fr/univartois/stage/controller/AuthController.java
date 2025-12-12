package fr.univartois.stage.controller;

import fr.univartois.stage.model.User;
import fr.univartois.stage.model.UserSession;
import fr.univartois.stage.service.AuthService;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.View;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import java.io.Serializable;
import java.util.Optional;

@Controller
@Path("auth")
@jakarta.enterprise.context.RequestScoped
public class AuthController implements Serializable {

    @Inject
    private AuthService authService;

    @Inject
    private UserSession userSession;

    @GET
    @Path("login")
    @View("login.jsp")
    public void showLoginForm() {
    }

    @POST
    @Path("login")
    public String login(@FormParam("login") String login, @FormParam("password") String password) {
        Optional<User> user = authService.authenticate(login, password);

        if (user.isPresent()) {
            userSession.setUser(user.get());
            return "redirect:/"; // Redirect to home on success
        } else {
            // For simplicity, just reload login with error (query param or binding result
            // could be used)
            return "redirect:/auth/login?error=true";
        }
    }

    @GET
    @Path("logout")
    public String logout() {
        userSession.logout();
        return "redirect:/auth/login";
    }
}
