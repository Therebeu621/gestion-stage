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
@Path("company")
@RequestScoped
public class CompanyController {

    @Inject
    StageService stageService;

    @Inject
    Models models;

    @GET
    @Path("/{name}")
    @View("company_detail.jsp")
    public void companyDetail(@PathParam("name") String name) {
        var entreprise = stageService.findEntrepriseByName(name);
        if (entreprise == null) {
            // Fallback if not found, though in this context we assume it exists if link was
            // clicked
            // We could redirect or show 404, but for now we'll just return
            return;
        }

        models.put("companyName", entreprise.getNom());
        models.put("entreprise", entreprise);
        // We can access stages via entreprise.getStages() in the view
    }
}
