package fr.univartois.stage.model;

@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "stages")
public class StageEntry {

    @jakarta.persistence.Id
    @jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String nomEtudiant;
    private String prenomEtudiant;
    private String mailUniversitaire;
    private String dateDebut;
    private String dateFin;
    private String formation;
    private String prenomEnseignantReferent;
    private String nomEtablissementAccueil;
    private String communeEtablissement;
    private String codePostal;
    private boolean accord;

    public StageEntry() {
        // Constructeur vide requis par JPA
    }

    public StageEntry(String nomEtudiant,
            String prenomEtudiant,
            String mailUniversitaire,
            String dateDebut,
            String dateFin,
            String formation,
            String prenomEnseignantReferent,
            String nomEtablissementAccueil,
            String communeEtablissement,
            String codePostal,
            boolean accord) {
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
        this.accord = accord;
    }

    public boolean isAccord() {
        return accord;
    }

    public void setAccord(boolean accord) {
        this.accord = accord;
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
