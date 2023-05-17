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
    }

    public void makeQuestChoice(int choiceId) {

        if (model.getCurrentStep() == null) {
            throw new IllegalStateException("No current step.");
        }

        if (choiceId >= model.getCurrentStep().getChoices().size()) {
            throw new IndexOutOfBoundsException();
        }

        QuestChoice choiceMade = model.getCurrentStep().getChoices().get(choiceId);
        model.setCurrentStep(choiceMade.getNextStep());
        model.getCurrentStep().setPreviousChoiceDescription(choiceMade.getDescription());
    }

    public QuestStep getCurentQuestStep() {
        return model.getCurrentStep();
    }
}
