package com.example.influxbackuprestore.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "restore")
@Getter
@Setter
public class RestoreProperties {

    private String host;
    private String db;
    private String rp;
    private String path;
    private String backupDirectory;
}
