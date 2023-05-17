package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyQuestServiceTest {
    @Test
    void initModelShouldSetRootToNull() {
        QuestModel model = new QuestModel();
        QuestService service = new EmptyQuestService(model);

        service.initModel();

        assertNull(service.getQuestModel().getRoot());
    }

    @Test
    void initModelShouldSetCurrentStepToNull() {
        QuestModel model = new QuestModel();
        QuestService service = new EmptyQuestService(model);

        service.initModel();

        assertNull(service.getCurentQuestStep());
    }
}
