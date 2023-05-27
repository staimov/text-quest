package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import com.staimov.textquest.model.SessionData;
import com.staimov.textquest.model.StepType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractQuestService implements QuestService {
    private static final Logger logger = LoggerFactory.getLogger(AbstractQuestService.class);

    final private QuestModel questModel;
    final private SessionData sessionData;

    public AbstractQuestService(QuestModel questModel, SessionData sessionData) {
        logger.debug("Inside AbstractQuestService(QuestModel questModel) constructor");

        if (questModel == null) {
            throw new IllegalArgumentException("Model can not be null");
        }

        if (sessionData == null) {
            throw new IllegalArgumentException("SessionData can not be null");
        }

        this.questModel = questModel;
        this.sessionData = sessionData;
    }

    @Override
    public abstract void initModel();

    @Override
    public int getStartCount() {
        synchronized (sessionData) {
            return sessionData.getStartCount();
        }
    }

    @Override
    public int getCompleteCount() {
        synchronized (sessionData) {
            return sessionData.getCompleteCount();
        }
    }

    @Override
    public String getPlayerName() {
        synchronized (sessionData) {
            return sessionData.getPlayerName();
        }
    }

    @Override
    public void setPlayerName(String playerName) {
        synchronized (sessionData) {
            sessionData.setPlayerName(playerName);
        }
    }

    protected QuestModel getQuestModel() {
        return questModel;
    }

    @Override
    public synchronized void startQuest() {
        sessionData.incrementStartCount();
        logger.info("Quest started: {}", questModel.getName());
    }

    public void completeQuest(StepType type) {
        synchronized (sessionData) {
            sessionData.incrementCompleteCount();

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
    }

    public QuestStep getNextQuestStep(long stepId, int choiceId) {
        synchronized (questModel) {
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
    }

    public String getChoiceDescription(long stepId, int choiceId) {
        synchronized (questModel) {
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
    }

    @Override
    public String getQuestName() {
        synchronized (questModel) {
            return questModel.getName();
        }
    }

    @Override
    public String getQuestDescription() {
        synchronized (questModel) {
            return questModel.getDescription();
        }
    }

    @Override
    public QuestStep getQuestRoot() {
        synchronized (questModel) {
            return questModel.getRoot();
        }
    }

    @Override
    public void setQuestRoot(QuestStep root) {
        synchronized (questModel) {
            questModel.setRoot(root);
        }
    }

    @Override
    public void resetCounters() {
        synchronized (sessionData) {
            sessionData.resetStartCount();
            sessionData.resetCompleteCount();
        }
    }

    @Override
    public QuestStep getQuestStep(long id) {
        synchronized (questModel) {
            return getQuestModel().getStep(id);
        }
    }

    @Override
    public boolean containsQuestStep(long id) {
        synchronized (questModel) {
            return getQuestModel().containsStep(id);
        }
    }

    @Override
    public void clearModel() {
        synchronized (questModel) {
            getQuestModel().setName(null);
            getQuestModel().setDescription(null);
            getQuestModel().clearSteps();
            setQuestRoot(null);
        }
    }

    @Override
    public void addQuestStep(QuestStep step) {
        synchronized (questModel) {
            getQuestModel().addStep(step);
        }
    }
}
