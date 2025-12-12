package fr.univartois.stage.controller;

import fr.univartois.stage.service.StageService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@Controller
@RequestScoped
public class StageController {

    @Inject
    Models models;

    @Inject
    StageService stageService;

    @Inject
    fr.univartois.stage.model.UserSession userSession;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @View("stages.jsp")
    public void list() {
        models.put("columns", stageService.headers());
        models.put("stages", stageService.findAll());
        if (userSession.isLoggedIn()) {
            models.put("user", userSession.getUser());
        }
    }
}
