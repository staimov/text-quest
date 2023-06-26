package com.staimov.textquest.config;

import com.staimov.textquest.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuestConfig {
    private static final Logger logger = LoggerFactory.getLogger(QuestConfig.class);

    @Value( "${text-quest.type}" )
    private String textQuestType;

    @Bean
    public QuestModel selectedQuestModel() {
        QuestModelFactory factory;

        if (textQuestType.isBlank()) {
            logger.debug("Quest type is blank");
        }
        else {
            logger.debug("Quest type is '{}'", textQuestType);
        }

        switch (textQuestType.trim().toLowerCase()) {
            case "investigate":
                factory = new InvestigateQuestModelFactory();
                break;

            case "unicorn":
                factory = new UnicornQuestModelFactory();
                break;

            default:
                factory = new DefaultQuestModelFactory();
        }

        return factory.createModel();
    }
}
