package example.greenwayb.bootstrap

import com.github.greenwayb.bootstrap.PropertySourceLocator
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.Ordered
import org.springframework.core.env.CompositePropertySource
import org.springframework.core.io.support.SpringFactoriesLoader

open class BootstrapApplicationListener : ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    companion object {
        private val LOG : Log = LogFactory.getLog(BootstrapApplicationListener::class.java)

        const val PROPERTY_NAME : String = "greenwaybProperties"

        private var initalized = false
    }

    override fun getOrder(): Int {
        return Ordered.HIGHEST_PRECEDENCE
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        if (!initalized) {
            val environment = applicationContext.environment

            val factories = SpringFactoriesLoader
                    .loadFactories(PropertySourceLocator::class.java, null)

            var ps = environment.propertySources.get(PROPERTY_NAME) as CompositePropertySource?
            if (null == ps) {
                ps = CompositePropertySource(PROPERTY_NAME)
                environment.propertySources.addLast(ps)
            }

            for (factory in factories) {
                ps.addPropertySource(factory.locate(environment))
            }
            initalized = true
            LOG.info("Property sources are ${environment.propertySources}")
        }
    }
}
