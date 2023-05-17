package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbstractQuestService implements QuestService, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(AbstractQuestService.class);

    private QuestModel questModel;

    public AbstractQuestService() {
        questModel = new QuestModel();
    }

    public AbstractQuestService(QuestModel questModel) {
        this.questModel = questModel;
    }

    @Override
    public abstract void initModel();

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("In afterPropertiesSet()");
        initModel();
    }

    public QuestModel getQuestModel() {
        return questModel;
    }

    @Override
    public void setQuestModel(QuestModel questModel) {
        this.questModel = questModel;
    }

    public void restartQuest() {
        questModel.restart();
        logger.info("Quest started: {}", questModel.getName());
    }

    public void resetQuest() {
        questModel.reset();
        logger.debug("Quest reset");
    }

    public void makeQuestChoice(int choiceId) {
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

    public QuestStep getCurentQuestStep() {
        return questModel.getCurrentStep();
    }
}
