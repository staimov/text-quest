package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractQuestService implements QuestService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractQuestService.class);

    final private QuestModel model;

    public AbstractQuestService(QuestModel model) {
        this.model = model;
    }

    @Override
    public abstract void initModel();

    public QuestModel getQuestModel() {
        return model;
    }

    public void restartQuest() {
        model.restart();
        logger.info("Quest started: {}", model.getName());
    }

    public void resetQuest() {
        model.reset();
    }

    public void nextQuestStep(int choiceId) {
        model.makeChoice(choiceId);
    }

    public QuestStep getCurentQuestStep() {
        return model.getCurrentStep();
    }
}
