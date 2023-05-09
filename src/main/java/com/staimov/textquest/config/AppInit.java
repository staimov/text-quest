package com.staimov.textquest.config;

import com.staimov.textquest.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements CommandLineRunner {
    private QuestService service;

    @Autowired
    public AppInit(QuestService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) throws Exception {
        service.initModel();
    }
}
