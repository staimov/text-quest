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

    @Value("${text-quest.type}")
    private String textQuestType;

    @Bean
    public QuestModel selectedQuestModel() {
        if (textQuestType == null || textQuestType.isBlank()) {
            logger.debug("Quest type is blank");
        }
        else {
            logger.debug("Quest type is '{}'", textQuestType);
        }

        QuestModelFactory factory = switch (textQuestType.trim().toLowerCase()) {
            case "investigate" -> new InvestigateQuestModelFactory();
            case "unicorn" -> new UnicornQuestModelFactory();
            default -> new DefaultQuestModelFactory();
        };

        return factory.createModel();
    }
}
