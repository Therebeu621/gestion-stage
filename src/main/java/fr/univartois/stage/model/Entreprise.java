package fr.univartois.stage.model;

import java.util.ArrayList;
import java.util.List;

public class Entreprise {

    private String id; // Nom slugifié pour URL
    private String nom;
    private String adresse;
    private String codePostal;
    private String commune;
    private String siteWeb;
    private String contact;
    private List<StageEntry> stages = new ArrayList<>();

    public Entreprise() {
    }

    public Entreprise(String nom, String commune, String codePostal) {
        this.nom = nom;
        this.commune = commune;
        this.codePostal = codePostal;
        this.id = slugify(nom);
        // Génération données
        generateFakeData();
    }

    private String slugify(String input) {
        if (input == null)
            return "";
        return input.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-");
    }

    private void generateFakeData() {
        this.adresse = "1, Rue de " + this.nom;
        this.siteWeb = "www." + this.id + ".com";
        this.contact = "contact@" + this.id + ".com";
    }

    public void addStage(StageEntry stage) {
        this.stages.add(stage);
    }

    // Générateurs accesseurs
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public String getCommune() {
        return commune;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public String getContact() {
        return contact;
    }

    public List<StageEntry> getStages() {
        return stages;
    }
}
