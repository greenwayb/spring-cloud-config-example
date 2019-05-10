package example.greenwayb.config.db;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(DbSettings.class)
@ComponentScan
public class DbAutoConfiguration {



}
