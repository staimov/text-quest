package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmptyQuestService extends AbstractQuestService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultQuestService.class);

    public EmptyQuestService(QuestModel model) {
        super(model);
    }

    @Override
    public synchronized void initModel() {
        logger.debug("Quest init: {}", getClass().getSimpleName());

        clearModel();

        resetCounters();
    }
}
