package example.greenwayb.bootstrap.eureka;

import example.greenwayb.config.eureka.EurekaClientConfigAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * Must do this with Spring Boot 2 or services cant register! (401)
 *
 * https://github.com/spring-cloud/spring-cloud-netflix/issues/2754
 */
@Configuration
@EnableWebSecurity
@Order(1)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private EurekaClientConfigAutoConfiguration config;

    public WebSecurityConfig(EurekaClientConfigAutoConfiguration config) {
        this.config = config;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser(config.getEurekaUsername()).password(config.getEurekaPassword())
                .authorities("ADMIN");
    }

}