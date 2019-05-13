package example.greenwayb.bootstrap;

import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

public interface PropertySourceLocator {

   PropertySource<?> locate(Environment environment);
}
