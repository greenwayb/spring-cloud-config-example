package example;

import example.greenwayb.config.eureka.EurekaClientConfigAutoConfiguration;
import example.greenwayb.config.db.DbSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// This will allow us to reinitialize this controller to get any new config
// values when the /refresh endpoint is POSTed to.
@RefreshScope
public class AppController {

    private DbSettings dbSettings;

    private EurekaClientConfigAutoConfiguration eurekaClientAutoConfiguration;

    public AppController( DbSettings dbSettings, EurekaClientConfigAutoConfiguration eurekaClientAutoConfiguration) {
        System.out.println(dbSettings);
        System.out.println(eurekaClientAutoConfiguration);
        this.dbSettings = dbSettings;
        this.eurekaClientAutoConfiguration = eurekaClientAutoConfiguration;
    }

    @Value("${info.foo}")
    private String fooProperty;

    @RequestMapping("/")
    public String hello() {
        return "Using [" + fooProperty + "] from config server";
    }


    @RequestMapping("/dbSettings")
    public String dbSettings() {
        return "Using [" + dbSettings + "] from property loading on startup";
    }

    @RequestMapping("/eurekaSettings")
    public String eurekaSettings() {
        return "Using [" + eurekaClientAutoConfiguration + "] from property loading on startup";
    }


}
