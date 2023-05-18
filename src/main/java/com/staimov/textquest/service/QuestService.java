package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;

public interface QuestService {
    void initModel();

    QuestModel getQuestModel();

    void setQuestModel(QuestModel questModel);

    void restartQuest();

    void resetQuest();

    void makeQuestChoice(int choiceId);

    QuestStep getCurrentQuestStep();

    boolean isQuestStarted();
}
