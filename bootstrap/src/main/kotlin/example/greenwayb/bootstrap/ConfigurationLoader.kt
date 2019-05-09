package com.github.greenwayb.bootstrap

import org.springframework.core.env.CompositePropertySource
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.Environment
import org.springframework.core.env.PropertySource
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.support.ResourcePropertySource
import java.io.IOException

object ConfigurationLoader {

    @JvmStatic
    fun getPropertySource(env: Environment, sourceName: String, filePrefix: String): PropertySource<*> {
        val environment = env as ConfigurableEnvironment
        try {
            val compositePropertySource = CompositePropertySource(sourceName)

            environment.activeProfiles
                    .map { ClassPathResource("$filePrefix-$it.properties") }
                    .filter { it.exists() }
                    .forEach { compositePropertySource.addPropertySource(ResourcePropertySource(it)) }

            val classPathResource = ClassPathResource("$filePrefix.properties")
            if (classPathResource.exists()) {
                compositePropertySource.addPropertySource(ResourcePropertySource(classPathResource))
            }

            return compositePropertySource

        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(Exception::class)
    @JvmStatic
    fun load(env: ConfigurableEnvironment, filePrefix: String) {
        env.activeProfiles
                .map { ClassPathResource("$filePrefix-$it.properties") }
                .filter { it.exists() }
                .forEach { env.propertySources.addLast(ResourcePropertySource(it)) }
        val classPathResource = ClassPathResource("$filePrefix.properties")
        if (classPathResource.exists()) {
            env.propertySources.addLast(ResourcePropertySource(classPathResource))
        }
    }

}
