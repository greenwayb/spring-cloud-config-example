package example.greenwayb.bootstrap;

import java.io.IOException;

import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

public class ConfigurationLoader {
    private static ConfigurationLoader ourInstance = new ConfigurationLoader();

    public static ConfigurationLoader getInstance() {
        return ourInstance;
    }

    private ConfigurationLoader() {
    }

    public static final PropertySource getPropertySource(ConfigurableEnvironment env, String sourceName, String filePrefix) {

        try {
            CompositePropertySource compositePropertySource = new CompositePropertySource(sourceName);

            for (String activeProfile : env.getActiveProfiles()) {
                // active profile, so we can detect dev, test etc
                String name = filePrefix+"-"+activeProfile+".properties";
                if (new ClassPathResource(name).exists()){
                    compositePropertySource.addPropertySource(new ResourcePropertySource(name));
                }
            }

            ClassPathResource classPathResource = new ClassPathResource(filePrefix + ".properties");
            if (classPathResource.exists()) {
                compositePropertySource.addPropertySource(new ResourcePropertySource(classPathResource));
            }

            return compositePropertySource;
        } catch (IOException iex){
            throw new RuntimeException(iex);
        }
    }
}
