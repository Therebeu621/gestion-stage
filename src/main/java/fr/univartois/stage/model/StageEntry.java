package fr.univartois.stage.model;

public class StageEntry {

    private final String nomEtudiant;
    private final String prenomEtudiant;
    private final String mailUniversitaire;
    private final String dateDebut;
    private final String dateFin;
    private final String formation;
    private final String prenomEnseignantReferent;
    private final String nomEtablissementAccueil;
    private final String communeEtablissement;
    private final String codePostal;

    public StageEntry(String nomEtudiant,
                      String prenomEtudiant,
                      String mailUniversitaire,
                      String dateDebut,
                      String dateFin,
                      String formation,
                      String prenomEnseignantReferent,
                      String nomEtablissementAccueil,
                      String communeEtablissement,
                      String codePostal) {
        this.nomEtudiant = nomEtudiant;
        this.prenomEtudiant = prenomEtudiant;
        this.mailUniversitaire = mailUniversitaire;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.formation = formation;
        this.prenomEnseignantReferent = prenomEnseignantReferent;
        this.nomEtablissementAccueil = nomEtablissementAccueil;
        this.communeEtablissement = communeEtablissement;
        this.codePostal = codePostal;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public String getPrenomEtudiant() {
        return prenomEtudiant;
    }

    public String getMailUniversitaire() {
        return mailUniversitaire;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public String getFormation() {
        return formation;
    }

    public String getPrenomEnseignantReferent() {
        return prenomEnseignantReferent;
    }

    public String getNomEtablissementAccueil() {
        return nomEtablissementAccueil;
    }

    public String getCommuneEtablissement() {
        return communeEtablissement;
    }

    public String getCodePostal() {
        return codePostal;
    }
}
