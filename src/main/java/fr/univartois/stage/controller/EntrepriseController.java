package fr.univartois.stage.controller;

import fr.univartois.stage.service.StageService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Controller
@Path("entreprises")
@RequestScoped
public class EntrepriseController {

    @Inject
    private Models models;

    @Inject
    private StageService stageService;

    @GET
    @View("entreprises.jsp")
    public void list() {
        models.put("entreprises", stageService.findAllEntreprises());
    }

    @GET
    @Path("{id}")
    @View("entreprise-detail.jsp")
    public void detail(@PathParam("id") String id) {
        var entreprise = stageService.getEntreprise(id);
        if (entreprise == null) {
            throw new jakarta.ws.rs.NotFoundException("L'entreprise " + id + " n'existe pas.");
        }
        models.put("entreprise", entreprise);
    }
}
