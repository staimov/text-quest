package com.staimov.textquest.config;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.service.DefaultQuestService;
import com.staimov.textquest.service.InvestigateQuestService;
import com.staimov.textquest.service.QuestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class QuestConfig {
    @Bean
    @SessionScope
    public QuestService selectedQuestService() {
        QuestService service = new InvestigateQuestService(new QuestModel());
        //QuestService service = new DefaultQuestService(new QuestModel());
        //QuestService service = new EmptyQuestService(new QuestModel());
        service.initModel();
        return service ;
    }
}
