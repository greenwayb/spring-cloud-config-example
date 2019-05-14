package example.greenwayb.config.eureka;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan
public class EurekaClientConfigAutoConfiguration {

        @Value("${myconfig.eureka.username}")
        private String eurekaUsername;

        @Value("${myconfig.eureka.password}")
        private String eurekaPassword;

        public String getEurekaUsername() {
                return eurekaUsername;
        }

        public String getEurekaPassword() {
                return eurekaPassword;
        }

        @Override
        public String toString() {
                return "EurekaClientAutoConfiguration{" +
                        "eurekaUsername='" + eurekaUsername + '\'' +
                        ", eurekaPassword='" + eurekaPassword + '\'' +
                        '}';
        }
}

