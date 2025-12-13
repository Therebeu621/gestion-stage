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
        LOGGER.info("Using Portable Authentication (UserDatabase). Database initialization skipped.");
        // Database logic disabled to match context.xml configuration
        /*
         * try (Connection conn = dataSource.getConnection()) {
         * // ... (SQL logic disabled) ...
         * } catch (Exception e) {
         * LOGGER.severe("Failed to initialize database: " + e.getMessage());
         * e.printStackTrace();
         * }
         */
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cleanup if necessary
    }
}
