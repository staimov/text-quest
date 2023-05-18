package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractQuestService implements QuestService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractQuestService.class);

    private QuestModel questModel;

    public AbstractQuestService(QuestModel questModel) {
        this.questModel = questModel;
    }

    @Override
    public abstract void initModel();

    public synchronized QuestModel getQuestModel() {
        return questModel;
    }

    @Override
    public synchronized void setQuestModel(QuestModel questModel) {
        this.questModel = questModel;
    }

    public synchronized void restartQuest() {
        questModel.restart();
        logger.info("Quest started: {}", questModel.getName());
    }

    public synchronized void resetQuest() {
        questModel.reset();
        logger.debug("Quest reset");
    }

    public synchronized void makeQuestChoice(int choiceId) {
        logger.debug("Make quest choice (choiceId = {})", choiceId);

        if (questModel.getCurrentStep() == null) {
            String message = "Current step is null";
            logger.error(message);
            throw new IllegalStateException(message);
        }

        if (choiceId >= questModel.getCurrentStep().getChoices().size()) {
            String message = String.format("Choice id %d is out of bounds", choiceId);
            logger.error(message);
            throw new IndexOutOfBoundsException(message);
        }

        QuestChoice choiceMade = questModel.getCurrentStep().getChoices().get(choiceId);
        questModel.setCurrentStep(choiceMade.getNextStep());
        questModel.getCurrentStep().setPreviousChoiceDescription(choiceMade.getDescription());

        logger.info("Choice is made: {}", questModel.getCurrentStep().getPreviousChoiceDescription());

        if (questModel.isPositiveFinal()) {
            logger.info("The quest is completed with a positive outcome");
        }
        else if (questModel.isNegativeFinal()) {
            logger.info("The quest is completed with a negative outcome");
        }
        else {
            logger.info("The quest is completed");
        }
    }

    public synchronized QuestStep getCurrentQuestStep() {
        return questModel.getCurrentStep();
    }

    public synchronized boolean isQuestStarted() {
        return getCurrentQuestStep() != null;
    }
}
