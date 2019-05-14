package example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(RefreshScope.class)
@ConditionalOnProperty(name = "spring.cloud.refresh.enabled", matchIfMissing = true)
public class CustomConfigRefreshAutoConfiguration {

    public CustomContextRefresher customContextRefresher(ConfigurableApplicationContext context, RefreshScope scope) {
        return new CustomContextRefresher(context, scope);
    }
}
