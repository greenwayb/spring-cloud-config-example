package example;

import example.greenwayb.config.db.DbSettings;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class ApplicationConfiguration {

    private DbSettings dbSettings;

    public ApplicationConfiguration(DbSettings dbSettings) {
        this.dbSettings = dbSettings;
    }
}
