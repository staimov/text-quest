package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
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
    public synchronized void restartQuest() {
        questModel.restart();
        startCount.incrementAndGet();
        logger.info("Quest started: {}", questModel.getName());
    }

    @Override
    public synchronized void resetQuest() {
        questModel.reset();
        logger.debug("Quest reset");
    }

    @Override
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

        if (questModel.isFinal()) {
            completeCount.incrementAndGet();
        }

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

    @Override
    public synchronized QuestStep getCurrentQuestStep() {
        return questModel.getCurrentStep();
    }

    @Override
    public synchronized boolean isQuestStarted() {
        return getCurrentQuestStep() != null;
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
    public void setQuestRoot(QuestStep root) {
        questModel.setRoot(root);
    }
}
