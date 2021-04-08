package dsw3;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import dsw3.resources.*;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("resources")
public class CampoMinadoApplication extends Application {
 
    private final Set<Object> singletons = new HashSet();
    private final Set<Class<?>> empty = new HashSet();

    public CampoMinadoApplication() {
        singletons.add(new BandeiraResource());
        singletons.add(new JogoResource());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }   
}
