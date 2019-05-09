package example


import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.cloud.context.scope.refresh.RefreshScope
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
@ConditionalOnClass(RefreshScope::class)
@ConditionalOnProperty(name = ["spring.cloud.refresh.enabled"], matchIfMissing = true)
open class CustomConfigRefreshAutoConfiguration {

    @Bean
    open fun customContextRefresher(context : ConfigurableApplicationContext, scope : RefreshScope) : CustomContextRefresher {
        return CustomContextRefresher(context,scope)
    }
}
