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
            if (entreprise == null) {
                // Entreprise non trouvée, arrêt du traitement
                return;
            }
        }

        models.put("companyName", entreprise.getNom());
        models.put("entreprise", entreprise);
        // Les stages sont accessibles via entreprise.getStages() dans la vue
    }
}
