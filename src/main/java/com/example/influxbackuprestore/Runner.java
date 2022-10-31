package com.example.influxbackuprestore;

import com.example.influxbackuprestore.scheduler.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements ApplicationRunner {

    @Autowired
    private SchedulerService schedulerService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        schedulerService.start();
    }
}
