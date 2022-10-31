package com.example.influxbackuprestore.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "backup")
@Getter
@Setter
public class BackupProperties {

    private String host;
    private String db;
    private String rp;

}
