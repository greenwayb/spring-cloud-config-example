package example.greenwayb.config.db;


//import example.greenwayb.bootstrap.ConfigurationLoader;
//import example.greenwayb.bootstrap.PropertySourceLocator;
import example.greenwayb.config.ConfigurationLoader;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

/**
 *
 */
public class DbPropertySourceLocator implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        return ConfigurationLoader.getPropertySource((ConfigurableEnvironment)environment, "database", "db");
    }
}
