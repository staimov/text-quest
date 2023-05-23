package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestStep;

public interface QuestService {
    void initModel();

    void restartQuest();

    void resetQuest();

    void makeQuestChoice(int choiceId);

    QuestStep getCurrentQuestStep();

    boolean isQuestStarted();

    int getStartCount();

    int getCompleteCount();

    String getPlayerName();

    void setPlayerName(String playerName);

    String getQuestName();

    String getQuestDescription();

    QuestStep getQuestRoot();

     void setQuestRoot(QuestStep root);

     void resetCounters();
}
