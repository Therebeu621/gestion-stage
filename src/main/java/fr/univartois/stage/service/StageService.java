package fr.univartois.stage.service;

import fr.univartois.stage.model.StageEntry;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

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

    private List<String> headers = List.of();
    private List<StageEntry> stages = List.of();

    @PostConstruct
    void init() {
        try {
            loadFromCsv();
        } catch (IOException e) {
            headers = defaultHeaders();
            stages = List.of();
        }
    }

    public List<String> headers() {
        return headers;
    }

    public List<StageEntry> findAll() {
        return stages;
    }

    public List<StageEntry> findByCompany(String companyName) {
        if (companyName == null || companyName.isBlank()) {
            return List.of();
        }
        return stages.stream()
                .filter(s -> companyName.equalsIgnoreCase(s.getNomEtablissementAccueil()))
                .filter(StageEntry::isAccord)
                .toList();
    }

    public StageEntry findOneCompany(String companyName) {
        return findByCompany(companyName).stream()
                .findFirst()
                .orElse(null);
    }

    public List<StageEntry> findByStudentEmail(String email) {
        if (email == null)
            return List.of();
        return stages.stream()
                .filter(s -> email.equalsIgnoreCase(s.getMailUniversitaire()))
                .toList();
    }

    public void toggleConsent(String email, int stageIndex) {
        List<StageEntry> studentStages = findByStudentEmail(email);
        if (stageIndex >= 0 && stageIndex < studentStages.size()) {
            StageEntry stage = studentStages.get(stageIndex);
            stage.setAccord(!stage.isAccord());
        }
    }

    private void loadFromCsv() throws IOException {
        System.out.println("DEBUG: Starting loadFromCsv...");
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("stages2025-anonyme.csv")) {
            if (is == null) {
                System.err.println("DEBUG: stages2025-anonyme.csv NOT FOUND in classpath!");
                throw new IOException("stages2025-anonyme.csv introuvable dans resources");
            }
            System.out.println("DEBUG: stages2025-anonyme.csv found.");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                List<String> lines = reader.lines()
                        .filter(line -> !line.isBlank())
                        .toList();

                System.out.println("DEBUG: Loaded " + lines.size() + " lines from CSV.");

                if (lines.isEmpty()) {
                    headers = defaultHeaders();
                    stages = new ArrayList<>(); // Ensure mutable
                    return;
                }

                headers = parseLine(lines.get(0));

                List<StageEntry> loaded = new ArrayList<>();
                for (String line : lines.subList(1, lines.size())) {
                    List<String> values = parseLine(line);
                    while (values.size() < 10) { // Ensure at least 10 columns
                        values.add("");
                    }

                    // Read 11th column for 'accord' if present
                    boolean accord = false;
                    if (values.size() > 10) {
                        accord = Boolean.parseBoolean(values.get(10));
                    }

                    loaded.add(new StageEntry(
                            values.get(0),
                            values.get(1),
                            values.get(2),
                            values.get(3),
                            values.get(4),
                            values.get(5),
                            values.get(6),
                            values.get(7),
                            values.get(8),
                            values.get(9),
                            accord));
                }

                stages = new ArrayList<>(loaded); // Ensure mutable
                System.out.println("DEBUG: Initialized stages list with " + stages.size() + " entries.");
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

    private List<String> defaultHeaders() {
        return List.of(
                "Nom étudiant",
                "Prénom étudiant",
                "Mail Universitaire étudiant",
                "Date Début Stage",
                "Date Fin Stage",
                "Formation",
                "Prénom Enseignant référent",
                "Nom Etablissement d'accueil",
                "Etablissement d'Accueil - Commune",
                "Code Postal");
    }

    // --- Entreprise Aggregation Logic ---

    public List<fr.univartois.stage.model.Entreprise> findAllEntreprises() {
        java.util.Map<String, fr.univartois.stage.model.Entreprise> map = new java.util.HashMap<>();

        for (StageEntry stage : stages) {
            String name = stage.getNomEtablissementAccueil();
            if (name == null || name.isBlank())
                continue;

            // Compute ID consistent with Entreprise logic (or reuse existing instance)
            if (!map.containsKey(name)) {
                // We create the entreprise using the first entry found for location
                map.put(name, new fr.univartois.stage.model.Entreprise(
                        name,
                        stage.getCommuneEtablissement(),
                        stage.getCodePostal()));
            }
            // Aggregation logic
            if (stage.isAccord()) {
                map.get(name).addStage(stage);
            }
        }

        List<fr.univartois.stage.model.Entreprise> result = new ArrayList<>(map.values());
        // Sort by name by default
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
        List<StageEntry> newStages = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            List<String> lines = reader.lines()
                    .filter(line -> !line.isBlank())
                    .toList();

            if (lines.isEmpty()) {
                return 0;
            }

            // Skip header if present (assuming first line is header)
            int start = 0;
            if (!lines.isEmpty() && lines.get(0).contains("Nom étudiant")) {
                start = 1;
            }

            for (String line : lines.subList(start, lines.size())) {
                List<String> values = parseLine(line);
                while (values.size() < 10) {
                    values.add("");
                }

                // Check responsible (Teacher Name is at index 6)
                // If currentUser is null or empty (should not happen for logged in admin), we might allow all or none.
                // Requirement: "Seuls les stages dont le responsable est l'utilisateur identifié sont importés"
                // mapping: login "johan" matches CSV "Johan"
                String responsableCsv = values.size() > 6 ? values.get(6) : "";
                if (currentUser != null && !currentUser.equalsIgnoreCase(responsableCsv)) {
                    continue; 
                }

                boolean accord = false;
                if (values.size() > 10) {
                    accord = Boolean.parseBoolean(values.get(10));
                }

                StageEntry newStage = new StageEntry(
                        values.get(0),
                        values.get(1),
                        values.get(2),
                        values.get(3),
                        values.get(4),
                        values.get(5),
                        values.get(6),
                        values.get(7),
                        values.get(8),
                        values.get(9),
                        accord);

                // Duplicate check
                boolean exists = newStages.stream()
                        .anyMatch(s -> s.getMailUniversitaire().equalsIgnoreCase(newStage.getMailUniversitaire()) &&
                                s.getDateDebut().equals(newStage.getDateDebut()) &&
                                s.getNomEtablissementAccueil().equalsIgnoreCase(newStage.getNomEtablissementAccueil()));

                                
                if (!exists) {
                    newStages.add(newStage);
                }
            }

            // Ajout (Append) à la liste existante avec vérification de doublons globale
            for (StageEntry s : newStages) {
                boolean alreadyInGlobal = stages.stream()
                        .anyMatch(existing -> existing.getMailUniversitaire().equalsIgnoreCase(s.getMailUniversitaire()) &&
                                existing.getDateDebut().equals(s.getDateDebut()) &&
                                existing.getNomEtablissementAccueil().equalsIgnoreCase(s.getNomEtablissementAccueil()));

                if (!alreadyInGlobal) {
                    stages.add(s);
                    count++;
                }
            }
        }
        return count;
    }
}
