package fr.univartois.stage;

import jakarta.ws.rs.ApplicationPath;
import org.eclipse.krazo.jersey.bootstrap.KrazoJerseyFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("mvc")
public class MvcApplication extends ResourceConfig {

    public MvcApplication() {
        packages("fr.univartois.stage");
        register(KrazoJerseyFeature.class);
    }
}
