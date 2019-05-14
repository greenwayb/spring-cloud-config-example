package example.greenwayb.config.eureka;

//import example.greenwayb.bootstrap.ConfigurationLoader;
//import example.greenwayb.bootstrap.PropertySourceLocator;
import example.greenwayb.config.ConfigurationLoader;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class EurekaClientPropertySourceLocator implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        return ConfigurationLoader.getPropertySource((ConfigurableEnvironment)environment, "eurekaClient", "eureka-client");
    }
}
