package example.greenwayb.config.eureka;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
@EnableDiscoveryClient
public class EurekaClientAutoConfiguration {

        @Value("${myconfig.eureka.username}")
        private String eurekaUsername;

        @Value("${myconfig.eureka.password}")
        private String eurekaPassword;

        @Override
        public String toString() {
                return "EurekaClientAutoConfiguration{" +
                        "eurekaUsername='" + eurekaUsername + '\'' +
                        ", eurekaPassword='" + eurekaPassword + '\'' +
                        '}';
        }
}

