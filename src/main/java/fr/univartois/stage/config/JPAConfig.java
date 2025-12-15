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
    // @jakarta.enterprise.context.RequestScoped // Careful with ApplicationScoped
    // Service using RequestScoped EM without proper proxy handling in some old weld
    // versions, but usually fine.
    // Actually, for ApplicationScoped service, it's better to inject
    // EntityManagerFactory or handle EM creation per method or use RequestScoped
    // service.
    // However, simplest fix: Produce RequestScoped EM, and CDI proxy handles it.
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
