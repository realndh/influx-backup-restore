package com.example.influxbackuprestore.scheduler;

import com.example.influxbackuprestore.properties.BackupProperties;
import com.example.influxbackuprestore.properties.RestoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
@Slf4j
public class SchedulerService {

    private final BackupProperties backupProperties;

    private final RestoreProperties restoreProperties;

    public SchedulerService(BackupProperties backupProperties, RestoreProperties restoreProperties) {
        this.backupProperties = backupProperties;
        this.restoreProperties = restoreProperties;
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24, initialDelay = 1000)
    public void scheduler() {

    }

    public void start() {
        ProcessBuilder builder = new ProcessBuilder();
        StringBuffer backupBuffer = new StringBuffer();
        StringBuffer targetBuffer = new StringBuffer();
        StringBuilder output = new StringBuilder();

        boolean isWindows = isWindows();
        if (isWindows) {
            backupBuffer.append("influxd.exe backup -portable");
            backupBuffer.append(" -host " + backupProperties.getHost());
            backupBuffer.append(" -db " + backupProperties.getDb());
            backupBuffer.append(" -rp " + backupProperties.getRp());
            backupBuffer.append(" " + restoreProperties.getBackupDirectory() + File.separator + System.currentTimeMillis());

        } else {
            backupBuffer.append("/opt/homebrew/opt/influxdb@1/bin/influxd backup -portable");
            backupBuffer.append(" -host " + backupProperties.getHost());
            backupBuffer.append(" -db " + backupProperties.getDb());
            backupBuffer.append(" -rp " + backupProperties.getRp());
            backupBuffer.append(" " + restoreProperties.getBackupDirectory() + File.separator + System.currentTimeMillis());
        }

        log.info("command : {}", backupBuffer.toString());

        if (isWindows) {
            builder.command("cmd.exe", "/c", backupBuffer.toString());
        } else {
            builder.command("sh", "-c", backupBuffer.toString());
        }

        //builder.directory(new File(restoreProperties.getPath()));

        try {
            Process process = builder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line + System.getProperty("line.separator"));
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                log.info("{}", "Success");
                log.info("{}", output.toString());
            } else {
                log.info("{}", "Abnormal");
                log.info("{}", output.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isWindows() {
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        log.info("isWindows : {}", isWindows);
        return isWindows;
    }
}
