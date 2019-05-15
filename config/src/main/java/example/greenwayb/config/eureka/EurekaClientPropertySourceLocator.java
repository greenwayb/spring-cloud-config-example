package example.greenwayb.config.eureka;

import example.greenwayb.bootstrap.ConfigurationLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Use EnvironmentPostProcessor and the issue with cloud config via DiscoveryClient and security progperties not being loaded goes away
 */
public class EurekaClientPropertySourceLocator implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        environment.getPropertySources().addLast(ConfigurationLoader.getPropertySource(environment, "eurekaClient", "eureka-client"));
    }

}
