package example.greenwayb.config.eureka;

import com.github.greenwayb.bootstrap.ConfigurationLoader;
import com.github.greenwayb.bootstrap.PropertySourceLocator;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

/**
 *
 */
public class EurekaPropertySourceLocator implements PropertySourceLocator {

    @NotNull
    @Override
    public PropertySource<?> locate(@NotNull Environment environment) {
        return ConfigurationLoader.getPropertySource(environment, "eurekaClient", "eureka-client");
    }
}
