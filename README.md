# Mon Futur Stage

A Jakarta MVC application for managing internship data, running on Apache Tomcat 10.1+.

## Technology Stack

- **Framework**: Jakarta EE 10 (MVC 2.1, JAX-RS 3.1, CDI 4.0)
- **Server**: Apache Tomcat 10.1+ (Servlet 6.0 Container)
- **Implementations**:
  - **CDI**: Weld 5.1
  - **JAX-RS**: Jersey 3.1
  - **MVC**: Eclipse Krazo 3.0
- **Build**: Apache Maven 3.8+

## Prerequisites

- Java 17 or higher
- Apache Tomcat 10.1 or higher
- Apache Maven 3.8+

## Building the Application

To build the project and generate the WAR file:

```bash
mvn clean package
```

This will create `target/mon-futur-stage.war`.

## Deployment

1.  **Stop Tomcat** if it is running.
2.  Copy the generated WAR file to your Tomcat `webapps` directory:
    ```bash
    cp target/mon-futur-stage.war $CATALINA_HOME/webapps/
    ```
3.  **Start Tomcat**:
    ```bash
    $CATALINA_HOME/bin/startup.sh
    ```
4.  Access the application at:
    - Stages List: [http://localhost:8080/mon-futur-stage/mvc/](http://localhost:8080/mon-futur-stage/mvc/)
    - Companies List: [http://localhost:8080/mon-futur-stage/mvc/entreprises](http://localhost:8080/mon-futur-stage/mvc/entreprises)

## Data Configuration

The application loads internship data from `src/main/resources/stages2025-anonyme.csv`. This file is packaged inside the WAR.

## Project Structure

- `src/main/java`: Java source code (Controllers, Services, Models).
- `src/main/webapp`: Web resources (JSP views, `web.xml`, `beans.xml`).
- `src/main/resources`: Configuration and data files.
