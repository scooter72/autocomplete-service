package server.boot;

import server.AutoCompleteService;
import server.data.DictionaryFactory;
import server.statistics.InMemStatisticsService;
import server.data.FileDictionary;
import server.statistics.StatisticsServiceFactory;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Defines the components of a JAX-RS application.
 */
@ApplicationPath("/autocomplete")
public class RestEasyServices extends Application {

    private Set<Object> singletons = new HashSet<Object>();

    public RestEasyServices() {
        singletons.add(new AutoCompleteService(
                DictionaryFactory.getDictionary(),
                StatisticsServiceFactory.getStatisticsService()));
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }

    @Override
    public Set<Class<?>> getClasses() {
        return super.getClasses();
    }

    @Override
    public Map<String, Object> getProperties() {
        return super.getProperties();
    }
}