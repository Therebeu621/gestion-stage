package fr.univartois.stage.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JPAConfig {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("stagePU");

    @Produces
    @ApplicationScoped
    public EntityManagerFactory createEntityManagerFactory() {
        return emf;
    }

    @Produces
    @jakarta.enterprise.context.RequestScoped
    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }
}
