package example.greenwayb.bootstrap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;

public class BootstrapApplicationListener implements ApplicationContextInitializer, Ordered {

    public static final String PROPERTY_NAME = "greenwaybProperties";
    private static boolean initialized;
    private static final Log LOG = LogFactory.getLog(BootstrapApplicationListener.class);

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (!initialized) {
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            List<PropertySourceLocator> factories = SpringFactoriesLoader.loadFactories(PropertySourceLocator.class, null);

            CompositePropertySource ps = (CompositePropertySource)environment.getPropertySources().get(PROPERTY_NAME);
            if (ps == null) {
                ps = new CompositePropertySource(PROPERTY_NAME);
                environment.getPropertySources().addLast(ps);
            }

            for (PropertySourceLocator factory : factories){
                ps.addPropertySource(factory.locate(environment));
            }

            initialized = true;
            LOG.info("Property sources are " + environment.getPropertySources());
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
