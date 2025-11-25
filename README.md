# Mon Futur Stage

Application Jakarta MVC qui affiche le fichier `stages2025-anonyme.csv` via une page JSP.

## Prérequis
- Java 17+
- Maven 3.8+ (pour `mvn clean package`)
- Payara Micro 7 (jar téléchargé, ex: `~/Downloads/payara-micro-7.2025.1.jar`)

## Lancer l'application (Payara Micro)
```bash
mvn clean package
java -jar ~/Downloads/payara-micro-7.2025.1.jar --deploy target/mon-futur-stage.war
```

Puis ouvrir : `http://localhost:8080/mon-futur-stage/mvc/`

## Notes
- Le CSV est dans `src/main/resources/stages2025-anonyme.csv`.
- Le contrôleur MVC est exposé sous `/mvc` et la vue JSP est dans `src/main/webapp/WEB-INF/views/stages.jsp`.
