package org.example.Mp3Player;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ExitApplication {

    private final ConfigurableApplicationContext applicationContext;

    public ExitApplication(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void exit() {
        int exitCode = SpringApplication.exit(applicationContext, () -> 0); // exitCode может быть использован системой, если нужно
        System.exit(exitCode); // Важно вызвать System.exit для завершения JVM, даже если Spring Boot его уже запланировал.
    }
}
