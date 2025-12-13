package fr.univartois.stage.controller;

import fr.univartois.stage.model.UserSession;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import java.io.Serializable;

@Controller
@Path("auth")
@jakarta.enterprise.context.RequestScoped
public class AuthController implements Serializable {

    @Inject
    private UserSession userSession;

    @jakarta.inject.Inject
    private jakarta.servlet.http.HttpServletRequest request;

    @GET
    @Path("login")
    public String showLoginForm() {
        return "redirect:/login.jsp"; // Redirection vers JSP public (hors WEB-INF)
    }

    @GET
    @Path("logout")
    public String logout() {
        try {
            // Invalidation session Tomcat
            request.logout();
            request.getSession().invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Synchro bean CDI
        userSession.logout();
        return "redirect:/";
    }

    @GET
    @Path("landing")
    public String postLoginLanding() {
        return "redirect:/";
    }
}
