package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;

public interface QuestService {
    void initModel();

    QuestModel getQuestModel();

    void restartQuest();

    void resetQuest();

    void makeQuestChoice(int choiceId);

    QuestStep getCurentQuestStep();
}
