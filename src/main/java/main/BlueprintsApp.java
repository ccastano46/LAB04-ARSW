package main;

import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class BlueprintsApp {

    private final BlueprintsServices blueprintsServices;

    @Autowired
    public BlueprintsApp(BlueprintsServices blueprintsServices) {
        this.blueprintsServices = blueprintsServices;
    }
    public static void main(String[] args) {
        SpringApplication.run(BlueprintsApp.class, args);
    }
}
