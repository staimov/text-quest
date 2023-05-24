package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestStep;
import com.staimov.textquest.model.StepType;

public interface QuestService {
    void initModel();

    void startQuest();

    int getStartCount();

    int getCompleteCount();

    String getPlayerName();

    void setPlayerName(String playerName);

    String getQuestName();

    String getQuestDescription();

    QuestStep getQuestRoot();

     void setQuestRoot(QuestStep root);

     void resetCounters();

    QuestStep getQuestStep(long id);

    boolean containsQuestStep(long id);

    void clearModel();

    void addQuestStep(QuestStep step);

    QuestStep getNextQuestStep(long currentStepId, int choiceId);

    String getChoiceDescription(long stepId, int choiceId);

    void completeQuest(StepType type);
}
