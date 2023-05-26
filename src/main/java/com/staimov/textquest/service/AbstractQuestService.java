package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import com.staimov.textquest.model.StepType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractQuestService implements QuestService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractQuestService.class);

    final private QuestModel questModel;
    private final AtomicInteger startCount = new AtomicInteger(0);
    private final AtomicInteger completeCount = new AtomicInteger(0);
    private String playerName = "Homer";

    public AbstractQuestService(QuestModel questModel) {
        logger.debug("Inside AbstractQuestService(QuestModel questModel) constructor");

        if (questModel == null) {
            throw new IllegalArgumentException("Model can not be null");
        }
        this.questModel = questModel;
    }

    @Override
    public abstract void initModel();

    @Override
    public synchronized int getStartCount() {
        return startCount.get();
    }

    @Override
    public synchronized int getCompleteCount() {
        return completeCount.get();
    }

    @Override
    public synchronized String getPlayerName() {
        return playerName;
    }

    @Override
    public synchronized void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    protected QuestModel getQuestModel() {
        return questModel;
    }

    @Override
    public synchronized void startQuest() {
        startCount.incrementAndGet();
        logger.info("Quest started: {}", questModel.getName());
    }

    public synchronized void completeQuest(StepType type) {
        completeCount.incrementAndGet();

        if (type == StepType.GOOD) {
            logger.info("The quest is completed with a positive outcome");
        }
        else if (type == StepType.BAD) {
            logger.info("The quest is completed with a negative outcome");
        }
        else {
            logger.info("The quest is completed");
        }
    }

    public synchronized QuestStep getNextQuestStep(long stepId, int choiceId) {
        QuestStep nextStep = null;

        QuestStep currentStep = getQuestStep(stepId);

        if (currentStep != null) {
            if (choiceId < currentStep.getChoices().size() && choiceId >= 0) {
                nextStep = currentStep.getChoices().get(choiceId).getNextStep();
            }
            else {
                String message = String.format("choiceId %d is out of bounds", choiceId);
                logger.error(message);
                throw new IndexOutOfBoundsException(message);
            }
        }
        else {
            String message = String.format("Step with id=%d can not be found", stepId);
            logger.error(message);
            throw new ObjectNotFoundException(message);
        }

        return nextStep;
    }

    public synchronized String getChoiceDescription(long stepId, int choiceId) {
        String choiceDescription = null;

        QuestStep step = getQuestStep(stepId);

        if (step != null) {
            if (choiceId < step.getChoices().size() && choiceId >= 0) {
                choiceDescription = step.getChoices().get(choiceId).getDescription();
            }
            else {
                String message = String.format("Choice id %d is out of bounds", choiceId);
                logger.error(message);
                throw new IndexOutOfBoundsException(message);
            }
        }
        else {
            String message = String.format("Step with id=%d can not be found", stepId);
            logger.error(message);
            throw new ObjectNotFoundException(message);
        }

        return choiceDescription;
    }

    @Override
    public synchronized String getQuestName() {
        return questModel.getName();
    }

    @Override
    public synchronized String getQuestDescription() {
        return questModel.getDescription();
    }

    @Override
    public synchronized QuestStep getQuestRoot() {
        return questModel.getRoot();
    }

    @Override
    public synchronized void setQuestRoot(QuestStep root) {
        questModel.setRoot(root);
    }

    @Override
    public synchronized void resetCounters() {
        startCount.set(0);
        completeCount.set(0);
    }

    @Override
    public synchronized QuestStep getQuestStep(long id) {
        return getQuestModel().getStep(id);
    }

    @Override
    public synchronized boolean containsQuestStep(long id) {
        return getQuestModel().containsStep(id);
    }

    @Override
    public synchronized void clearModel() {
        getQuestModel().setName(null);
        getQuestModel().setDescription(null);
        getQuestModel().clearSteps();
        setQuestRoot(null);
    }

    @Override
    public synchronized void addQuestStep(QuestStep step) {
        getQuestModel().addStep(step);
    }
}
