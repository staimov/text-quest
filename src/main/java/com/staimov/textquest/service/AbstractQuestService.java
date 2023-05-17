package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractQuestService implements QuestService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractQuestService.class);

    private final QuestModel model;

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
        logger.debug("Quest reset");
    }

    public void makeQuestChoice(int choiceId) {
        logger.debug("Make quest choice (choiceId = {})", choiceId);

        if (model.getCurrentStep() == null) {
            String message = "Current step is null";
            logger.error(message);
            throw new IllegalStateException(message);
        }

        if (choiceId >= model.getCurrentStep().getChoices().size()) {
            String message = String.format("Choice id %d is out of bounds", choiceId);
            logger.error(message);
            throw new IndexOutOfBoundsException(message);
        }

        QuestChoice choiceMade = model.getCurrentStep().getChoices().get(choiceId);
        model.setCurrentStep(choiceMade.getNextStep());
        model.getCurrentStep().setPreviousChoiceDescription(choiceMade.getDescription());

        logger.info("Choice is made: {}", model.getCurrentStep().getPreviousChoiceDescription());
    }

    public QuestStep getCurentQuestStep() {
        return model.getCurrentStep();
    }
}
