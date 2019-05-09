package com.github.greenwayb.bootstrap

import org.springframework.core.env.Environment
import org.springframework.core.env.PropertySource

interface PropertySourceLocator {
    fun locate(environment: Environment): PropertySource<*>
}


