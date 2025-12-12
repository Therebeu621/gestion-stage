package fr.univartois.stage.controller;

import fr.univartois.stage.model.StageEntry;
import fr.univartois.stage.model.UserSession;
import fr.univartois.stage.service.StageService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.FormParam;

import java.util.List;

@Controller
@Path("my-stages")
@RequestScoped
public class MyStagesController {

    @Inject
    private UserSession userSession;

    @Inject
    private StageService stageService;

    @Inject
    private Models models;

    @GET
    @View("my_stages.jsp")
    public void myStages() {
        if (!userSession.isLoggedIn() || userSession.getUser().getEmail() == null) {
            return; // Or redirect to login
        }

        String email = userSession.getUser().getEmail();
        List<StageEntry> myStages = stageService.findByStudentEmail(email);
        models.put("stages", myStages);
    }

    @POST
    @Path("toggle-accord")
    public String toggleAccord(@FormParam("stageIndex") int index) {
        if (userSession.isLoggedIn() && userSession.getUser().getEmail() != null) {
            String email = userSession.getUser().getEmail();
            stageService.toggleConsent(email, index);
        }
        return "redirect:/my-stages";
    }
}
