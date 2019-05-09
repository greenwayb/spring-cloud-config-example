curl http://localhost:8080/

curl http://localhost:8080/dbSettings
 Using [DbSettings{username='commonuser', password='commonpassword'}] from property loading on startup

curl -X POST http://localhost:8080/actuator/refresh


Issue in our work is where using bootstapped properties to get to a eureka secured service.
