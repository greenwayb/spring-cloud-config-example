This Is Forked to provide an example of an issue that is raised here: [https://github.com/spring-cloud/spring-cloud-config/issues/1336](https://github.com/spring-cloud/spring-cloud-config/issues/1336)

# Explaining the codebase
The code that exists here is a cut down and generic version of the way that we have cloud config configured and leveraging items in our framework.

Eureka is started with security (we use Oauth2, but the example here uses BASIC to reduce complexity).
We have a number of custom property loaders in our framework, so that we can pull in helper modules that are self contained, each has a naming convention that allows for custom naming
rather than application.properties.  Examples of these are shown in the "config" module. The example here is the EurekaPropertySourceLocator, which loads values from eureka-client.properties.

This is then pulled in as a module dependency inside the "client" module. 

The "client" module, then makes use of the properties that got loaded:

eureka:
  client:
    serviceUrl:
      defaultZone: http://${myconfig.eureka.username}:${myconfig.eureka.password}@localhost:8761/eureka


This works fine on startup.

On performing a refresh, it will fail as an attempt is being made to talk to the cloud config via eureka, but it has now "lost" its properties.

The fix, is that the file: spring-cloud-config-example/client/src/main/java/example/CustomContextRefresher.java needs to add in the ability to load in the bootstrapped properties:

```
 private static final String[] DEFAULT_PROPERTY_SOURCES = new String[] {
            // order matters, if cli args aren't first, things get messy
            CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME,
            "defaultProperties",
            example.greenwayb.bootstrap.BootstrapApplicationListener.PROPERTY_NAME }; // <-- This is the fix!! Issue-1336
```

That is how we have had to perform a work around in our code base, essentially copying the ContextRefresher class and overriding it to add one small line of config.  

It would be better if there was a way to tell the existing ContextRefresher to look for additional properties (that were custom bootstrapped)!

  

# Test Scenario
1 Start Eureka Application
2 Start Server Application
3 Start Client Application

Check Config Loaded:
curl http://localhost:8080/dbSettings
 Using [DbSettings{username='commonuser', password='commonpassword'}] from property loading on startup


Perform Refresh
curl -X POST http://localhost:8080/actuator/refresh

With the CustomContextRefresher it will work, without it will error. 


Without it will fail:

```
2019-05-10 11:34:22.232  INFO 17957 --- [nio-8080-exec-2] com.netflix.discovery.DiscoveryClient    : Application version is -1: true
2019-05-10 11:34:22.232  INFO 17957 --- [nio-8080-exec-2] com.netflix.discovery.DiscoveryClient    : Getting all instance registry info from the eureka server
2019-05-10 11:34:22.240 ERROR 17957 --- [nio-8080-exec-2] c.n.d.s.t.d.RedirectingEurekaHttpClient  : Request execution error. endpoint=DefaultEndpoint{ serviceUrl='http://${myconfig.eureka.username}:${myconfig.eureka.password}@localhost:8761/eureka/}

java.lang.IllegalArgumentException: Illegal character in authority at index 7: http://${myconfig.eureka.username}:${myconfig.eureka.password}@localhost:8761/eureka/
	at java.net.URI.create(URI.java:852) ~[na:1.8.0_201]
	at com.sun.jersey.api.client.Client.resource(Client.java:434) ~[jersey-client-1.19.1.jar:1.19.1]
	at com.netflix.discovery.shared.transport.jersey.AbstractJerseyEurekaHttpClient.getApplicationsInternal(AbstractJerseyEurekaHttpClient.java:187) ~[eureka-client-1.9.8.jar:1.9.8]
	at com.netflix.discovery.shared.transport.jersey.AbstractJerseyEurekaHttpClient.getApplications(AbstractJerseyEurekaHttpClient.java:165) ~[eureka-client-1.9.8.jar:1.9.8]
	at com.netflix.discovery.shared.transport.decorator.EurekaHttpClientDecorator$6.execute(EurekaHttpClientDecorator.java:137) [eureka-client-1.9.8.jar:1.9.8]
	at com.netflix.discovery.shared.transport.decorator.MetricsCollectingEurekaHttpClient.execute(MetricsCollectingEurekaHttpClient.java:73) ~[eureka-client-1.9.8.jar:1.9.8]
	at com.netflix.discovery.shared.transport.decorator.EurekaHttpClientDecorator.getApplications(EurekaHttpClientDecorator.java:134) [eureka-client-1.9.8.jar:1.9.8]
	at com.netflix.discovery.shared.transport.decorator.EurekaHttpClientDecorator$6.execute(EurekaHttpClientDecorator.java:137) [eureka-client-1.9.8.jar:1.9.8]
	at com.netflix.discovery.shared.transport.decorator.RedirectingEurekaHttpClient.executeOnNewServer(RedirectingEurekaHttpClient.java:118) ~[eureka-client-1.9.8.jar:1.9.8]
	at com.netflix.discovery.shared.transport.decorator.RedirectingEurekaHttpClient.execute(RedirectingEurekaHttpClient.java:79) ~[eureka-client-1.9.8.jar:1.9.8]
	at com.netflix.discovery.shared.transport.decorator.EurekaHttpClientDecorator.getApplications(EurekaHttpClientDecorator.java:134) [eureka-client-1.9.8.jar:1.9.8]
	at com.netflix.discovery.shared.transport.decorator.EurekaHttpClientDecorator$6.execute(EurekaHttpClientDecorator.java:137) [eureka-client-1.9.8.jar:1.9.8]
```	
	
Now go to the class, client/src/main/java/example/CustomContextRefresher.java and ensure it loads in the properties (uncomment the line with BootstrapApplicationListener.PROPERTY_NAME)
	
	
```
 private static final String[] DEFAULT_PROPERTY_SOURCES = new String[] {
            // order matters, if cli args aren't first, things get messy
            CommandLinePropertySource.COMMAND_LINE_PROPERTY_SOURCE_NAME,
            "defaultProperties",
            example.greenwayb.bootstrap.BootstrapApplicationListener.PROPERTY_NAME }; // <-- This is the fix!! Issue-1336
```


Now re-run the scenraio and the refresh will work correctly.