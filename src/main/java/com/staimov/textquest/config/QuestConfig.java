package com.staimov.textquest.config;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.service.DefaultQuestService;
import com.staimov.textquest.service.InvestigateQuestService;
import com.staimov.textquest.service.QuestService;
import com.staimov.textquest.service.UnicornQuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class QuestConfig {
    private final QuestModel model;

    @Autowired
    public QuestConfig(QuestModel model) {
        this.model = model;
    }

    @Bean
    @SessionScope
    public QuestService selectedQuestService() {
        QuestService service = new UnicornQuestService(model);
        //QuestService service = new InvestigateQuestService(model);
        //QuestService service = new DefaultQuestService(model);
        service.initModel();
        return service;
    }
}
