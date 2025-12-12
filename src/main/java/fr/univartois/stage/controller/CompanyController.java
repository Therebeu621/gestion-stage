package fr.univartois.stage.controller;

import fr.univartois.stage.model.StageEntry;
import fr.univartois.stage.service.StageService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mvc.Controller;
import jakarta.mvc.Models;
import jakarta.mvc.View;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.List;

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
        StageEntry companyInfo = stageService.findOneCompany(name);
        List<StageEntry> stages = stageService.findByCompany(name).stream()
                .filter(StageEntry::isAccord)
                .toList();

        models.put("companyName", name);
        models.put("companyInfo", companyInfo); // For address, etc.
        models.put("stages", stages);
    }
}
