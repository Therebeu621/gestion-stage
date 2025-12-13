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
    public void list(
            @jakarta.ws.rs.QueryParam("sort") String sort,
            @jakarta.ws.rs.QueryParam("dir") String dir,
            @jakarta.ws.rs.QueryParam("formation") java.util.List<String> formations) {
        // Retrieve all stages
        var allStages = stageService.findAll();

        // Group by Company+City and collect formations
        var grouped = new java.util.HashMap<String, CompanyViewModel>();

        for (var stage : allStages) {
            String key = stage.getNomEtablissementAccueil().toUpperCase() + "|"
                    + stage.getCommuneEtablissement().toUpperCase();

            grouped.compute(key, (k, v) -> {
                if (v == null) {
                    v = new CompanyViewModel(
                            stage.getNomEtablissementAccueil(),
                            stage.getCommuneEtablissement(),
                            stage.getCodePostal());
                }
                v.addFormation(stage.getFormation());
                return v;
            });
        }

        var companies = new java.util.ArrayList<>(grouped.values());

        // Filter companies if formations are selected
        if (formations != null && !formations.isEmpty()) {
            // "en meme temps" -> Intersection filter: Company must have ALL selected
            // formations
            companies.removeIf(c -> !c.getFormationSet().containsAll(formations));
        }

        // Sorting
        String sortField = (sort == null || sort.isBlank()) ? "name" : sort.toLowerCase();
        boolean desc = "desc".equalsIgnoreCase(dir);

        java.util.Comparator<CompanyViewModel> comparator = switch (sortField) {
            case "city" -> java.util.Comparator.comparing(CompanyViewModel::getCity, String.CASE_INSENSITIVE_ORDER);
            case "level" -> java.util.Comparator.comparing(CompanyViewModel::getFormations,
                    String.CASE_INSENSITIVE_ORDER);
            default -> java.util.Comparator.comparing(CompanyViewModel::getName, String.CASE_INSENSITIVE_ORDER);
        };

        if (desc) {
            comparator = comparator.reversed();
        }
        companies.sort(comparator);

        models.put("columns", java.util.List.of("Nom Entreprise", "Commune", "Code Postal", "Niveau", "Action"));
        models.put("stages", companies);
        models.put("sortField", sortField);
        models.put("sortDir", desc ? "desc" : "asc");
        models.put("selectedFormations", formations); // Pass back to view

        if (userSession.isLoggedIn()) {
            models.put("user", userSession.getUser());
        }
    }

    public static class CompanyViewModel {
        private String name;
        private String city;
        private String zip;
        private java.util.Set<String> formationSet = new java.util.HashSet<>();

        public CompanyViewModel(String name, String city, String zip) {
            this.name = name;
            this.city = city;
            this.zip = zip;
        }

        public void addFormation(String f) {
            if (f != null && !f.isBlank()) {
                formationSet.add(f.trim());
            }
        }

        public String getName() {
            return name;
        }

        public String getCity() {
            return city;
        }

        public String getZip() {
            return zip;
        }

        public String getFormations() {
            return String.join(", ", new java.util.TreeSet<>(formationSet));
        }

        public java.util.Set<String> getFormationSet() {
            return formationSet;
        }

        // Needed for JSP EL compatibility if it expects 'nomEtablissementAccueil' style
        // from before?
        // No, I will update JSP. But wait, I changed the List content type!
        // The JSP currently uses: stage.nomEtablissementAccueil
        // I should provide getNomEtablissementAccueil() alias to match JSP OR update
        // JSP.
        // It's cleaner to update JSP to use the new simple property names.

        // Compatibility getters to minimize JSP breakage during transition if needed,
        // but I will update JSP in the next step anyway.
        public String getNomEtablissementAccueil() {
            return name;
        }

        public String getCommuneEtablissement() {
            return city;
        }

        public String getCodePostal() {
            return zip;
        }
    }
}
