FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:10.1-jdk17
LABEL maintainer="groupe-jakarta-mvc-1"

# Supprimer les apps par défaut de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copier le WAR buildé
COPY --from=builder /app/target/mon-futur-stage.war /usr/local/tomcat/webapps/mon-futur-stage.war

# Copier la configuration tomcat-users.xml avec les utilisateurs préconfigurés
COPY docker/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

EXPOSE 8080

CMD ["catalina.sh", "run"]
