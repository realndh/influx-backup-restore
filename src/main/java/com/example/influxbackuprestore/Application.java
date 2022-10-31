package com.example.influxbackuprestore;

import com.example.influxbackuprestore.properties.BackupProperties;
import com.example.influxbackuprestore.properties.RestoreProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties({BackupProperties.class, RestoreProperties.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
