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
                .toList();
    }

    public StageEntry findOneCompany(String companyName) {
        return findByCompany(companyName).stream()
                .findFirst()
                .orElse(null);
    }

    private void loadFromCsv() throws IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("stages2025-anonyme.csv")) {
            if (is == null) {
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

                List<StageEntry> loaded = new ArrayList<>();
                for (String line : lines.subList(1, lines.size())) {
                    List<String> values = parseLine(line);
                    while (values.size() < EXPECTED_COLUMNS) {
                        values.add("");
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
                            values.get(9)));
                }

                stages = loaded;
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
}
