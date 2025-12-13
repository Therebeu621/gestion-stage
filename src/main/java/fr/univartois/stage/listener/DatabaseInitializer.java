package fr.univartois.stage.listener;

import jakarta.annotation.Resource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Logger;

//@WebListener
public class DatabaseInitializer implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    @Resource(name = "jdbc/StageDB")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Initializing Database...");
        try (Connection conn = dataSource.getConnection()) {

            // 1. Create users table if not exists
            try (Statement stmt = conn.createStatement()) {
                String sql = "CREATE TABLE IF NOT EXISTS users (" +
                        "login VARCHAR(50) PRIMARY KEY, " +
                        "password VARCHAR(100) NOT NULL, " +
                        "role_name VARCHAR(50) NOT NULL, " +
                        "prenom VARCHAR(100), " +
                        "email VARCHAR(100))";
                stmt.execute(sql);
                LOGGER.info("Table 'users' verified.");
            }

            // 2. Check if 'etudiant' exists
            boolean userExists = false;
            String checkSql = "SELECT 1 FROM users WHERE login = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
                pstmt.setString(1, "etudiant");
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        userExists = true;
                    }
                }
            }

            // 3. Create 'etudiant' if not exists
            if (!userExists) {
                String insertSql = "INSERT INTO users (login, password, role_name, prenom, email) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                    pstmt.setString(1, "etudiant");
                    pstmt.setString(2, "password"); // In production, hash this! But for this task, cleartext or simple
                                                    // match.
                    pstmt.setString(3, "STUDENT");
                    pstmt.setString(4, "Etudiant Test");
                    pstmt.setString(5, "etudiant@example.com");
                    pstmt.executeUpdate();
                    LOGGER.info("User 'etudiant' created.");
                }
            } else {
                LOGGER.info("User 'etudiant' already exists.");
            }

        } catch (Exception e) {
            LOGGER.severe("Failed to initialize database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup if necessary
    }
}
