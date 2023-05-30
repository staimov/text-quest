package com.staimov.textquest.config;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.SessionData;
import com.staimov.textquest.service.DefaultQuestService;
import com.staimov.textquest.service.InvestigateQuestService;
import com.staimov.textquest.service.QuestService;
import com.staimov.textquest.service.UnicornQuestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestConfig {
    @Bean(initMethod = "initModel")
    public QuestService selectedQuestService(QuestModel model, SessionData sessionData) {
        //return new UnicornQuestService(model, sessionData);
        return new InvestigateQuestService(model, sessionData);
        //return new DefaultQuestService(model, sessionData);
    }
}
