package com.staimov.textquest.config;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.service.DefaultQuestService;
import com.staimov.textquest.service.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestConfig {
    private final QuestModel model;

    @Autowired
    public QuestConfig(QuestModel model) {
        this.model = model;
    }

    @Bean
    public QuestService selectedQuestService() {
        return new DefaultQuestService(model);
        //return new EmptyQuestService(model);
    }
}
