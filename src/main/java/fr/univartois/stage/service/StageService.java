package fr.univartois.stage.service;

import fr.univartois.stage.model.StageEntry;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class StageService {

    private static final int EXPECTED_COLUMNS = 10;

    @Inject
    private EntityManagerFactory emf;

    @Inject
    private EntityManager em;

    private List<String> headers = List.of();

    @PostConstruct
    void init() {
        // Create a temporary EntityManager for initialization (outside of HTTP request)
        try (EntityManager initEm = emf.createEntityManager()) {
            try {
                long count = initEm.createQuery("SELECT COUNT(s) FROM StageEntry s", Long.class).getSingleResult();
                if (count == 0) {
                    System.out.println("DEBUG: DB is empty. Loading initial data from CSV...");
                    loadFromCsvAndPersist(initEm);
                } else {
                    System.out.println("DEBUG: DB already contains data (" + count + " entries). Skipping CSV load.");
                    headers = defaultHeaders();
                }
            } catch (Exception e) {
                e.printStackTrace();
                headers = defaultHeaders();
            }
        }
    }

    public List<String> headers() {
        return headers;
    }

    public List<StageEntry> findAll() {
        return em.createQuery("SELECT s FROM StageEntry s", StageEntry.class).getResultList();
    }

    public List<StageEntry> findByCompany(String companyName) {
        if (companyName == null || companyName.isBlank()) {
            return List.of();
        }
        return em.createQuery(
                "SELECT s FROM StageEntry s WHERE UPPER(s.nomEtablissementAccueil) = UPPER(:name) AND s.accord = true",
                StageEntry.class)
                .setParameter("name", companyName)
                .getResultList();
    }

    public StageEntry findOneCompany(String companyName) {
        List<StageEntry> results = findByCompany(companyName);
        return results.isEmpty() ? null : results.get(0);
    }

    public List<StageEntry> findByStudentEmail(String email) {
        if (email == null)
            return List.of();
        return em
                .createQuery("SELECT s FROM StageEntry s WHERE UPPER(s.mailUniversitaire) = UPPER(:email)",
                        StageEntry.class)
                .setParameter("email", email)
                .getResultList();
    }

    public void toggleConsent(String email, int stageIndex) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            List<StageEntry> studentStages = findByStudentEmail(email);
            if (stageIndex >= 0 && stageIndex < studentStages.size()) {
                StageEntry stage = studentStages.get(stageIndex);
                stage.setAccord(!stage.isAccord());
                em.merge(stage); // Save change
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive())
                tx.rollback();
            e.printStackTrace();
        }
    }

    private void loadFromCsvAndPersist(EntityManager entityManager) throws IOException {
        System.out.println("DEBUG: Starting loadFromCsvAndPersist...");
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("stages2025-anonyme.csv")) {
            if (is == null) {
                System.err.println("DEBUG: stages2025-anonyme.csv NOT FOUND in classpath!");
                throw new IOException("stages2025-anonyme.csv introuvable dans resources");
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                List<String> lines = reader.lines()
                        .filter(line -> !line.isBlank())
                        .toList();

                if (lines.isEmpty()) {
                    headers = defaultHeaders();
                    return;
                }

                headers = parseLine(lines.get(0));

                EntityTransaction tx = entityManager.getTransaction();
                tx.begin();
                try {
                    for (String line : lines.subList(1, lines.size())) {
                        StageEntry entry = parseToEntry(line);
                        if (entry != null) {
                            entityManager.persist(entry);
                        }
                    }
                    tx.commit();
                } catch (Exception e) {
                    if (tx.isActive())
                        tx.rollback();
                    throw e;
                }
            }
        }
    }

    private List<String> parseLine(String line) {
        List<String> columns = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                columns.add(clean(current.toString()));
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        columns.add(clean(current.toString()));
        return columns;
    }

    private String clean(String value) {
        String cleaned = value.trim();
        if (cleaned.startsWith("\"") && cleaned.endsWith("\"") && cleaned.length() >= 2) {
            cleaned = cleaned.substring(1, cleaned.length() - 1);
        }
        return cleaned;
    }

    private StageEntry parseToEntry(String line) {
        List<String> values = parseLine(line);
        while (values.size() < 10) {
            values.add("");
        }
        boolean accord = values.size() > 10 && Boolean.parseBoolean(values.get(10));

        return new StageEntry(
                values.get(0), values.get(1), values.get(2), values.get(3),
                values.get(4), values.get(5), values.get(6), values.get(7),
                values.get(8), values.get(9), accord);
    }

    private List<String> defaultHeaders() {
        return List.of(
                "Nom étudiant", "Prénom étudiant", "Mail Universitaire étudiant",
                "Date Début Stage", "Date Fin Stage", "Formation",
                "Prénom Enseignant référent", "Nom Etablissement d'accueil",
                "Etablissement d'Accueil - Commune", "Code Postal");
    }

    // --- Entreprise Aggregation Logic ---

    public List<fr.univartois.stage.model.Entreprise> findAllEntreprises() {
        // We fetch all stages from DB
        List<StageEntry> allStages = findAll();

        java.util.Map<String, fr.univartois.stage.model.Entreprise> map = new java.util.HashMap<>();

        for (StageEntry stage : allStages) {
            String name = stage.getNomEtablissementAccueil();
            if (name == null || name.isBlank())
                continue;

            if (!map.containsKey(name)) {
                map.put(name, new fr.univartois.stage.model.Entreprise(
                        name,
                        stage.getCommuneEtablissement(),
                        stage.getCodePostal()));
            }
            if (stage.isAccord()) {
                map.get(name).addStage(stage);
            }
        }

        List<fr.univartois.stage.model.Entreprise> result = new ArrayList<>(map.values());
        result.sort((e1, e2) -> e1.getNom().compareToIgnoreCase(e2.getNom()));
        return result;
    }

    public fr.univartois.stage.model.Entreprise getEntreprise(String id) {
        return findAllEntreprises().stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public fr.univartois.stage.model.Entreprise findEntrepriseByName(String name) {
        return findAllEntreprises().stream()
                .filter(e -> e.getNom().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public int importStages(InputStream inputStream, String currentUser) throws IOException {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            List<String> lines = reader.lines().filter(l -> !l.isBlank()).toList();
            if (lines.isEmpty())
                return 0;

            int start = (lines.get(0).contains("Nom étudiant")) ? 1 : 0;

            EntityTransaction tx = em.getTransaction();
            try {
                tx.begin();
                for (String line : lines.subList(start, lines.size())) {
                    StageEntry newStage = parseToEntry(line);

                    // Permission Check
                    String responsableCsv = newStage.getPrenomEnseignantReferent();
                    if (currentUser != null && !currentUser.equalsIgnoreCase(responsableCsv)) {
                        continue;
                    }

                    // Duplicate check via DB Query
                    long exists = em.createQuery(
                            "SELECT COUNT(s) FROM StageEntry s WHERE UPPER(s.mailUniversitaire) = UPPER(:mail) AND s.dateDebut = :date AND UPPER(s.nomEtablissementAccueil) = UPPER(:company)",
                            Long.class)
                            .setParameter("mail", newStage.getMailUniversitaire())
                            .setParameter("date", newStage.getDateDebut())
                            .setParameter("company", newStage.getNomEtablissementAccueil())
                            .getSingleResult();

                    if (exists == 0) {
                        em.persist(newStage);
                        count++;
                    }
                }
                tx.commit();
            } catch (Exception e) {
                if (tx.isActive())
                    tx.rollback();
                throw e;
            }
        }
        return count;
    }
}
