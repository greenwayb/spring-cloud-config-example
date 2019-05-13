package example.greenwayb.config.eureka;

import example.greenwayb.bootstrap.ConfigurationLoader;
import example.greenwayb.bootstrap.PropertySourceLocator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

/**
 *
 */
public class EurekaClientPropertySourceLocator implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        return ConfigurationLoader.getPropertySource((ConfigurableEnvironment)environment, "eurekaClient", "eureka-client");
    }
}
