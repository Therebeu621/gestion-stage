package fr.univartois.stage.controller;

import fr.univartois.stage.service.StageService;
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
public class StageController {

    @Inject
    Models models;

    @Inject
    StageService stageService;

    @GET
    @Produces(MediaType.TEXT_HTML)
    @View("stages.jsp")
    public void list() {
        models.put("headers", stageService.headers());
        models.put("stages", stageService.findAll());
    }
}
