package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyQuestServiceTest {

    private final QuestService service =
            new EmptyQuestService(new QuestModel());

    @Test
    void initModelShouldSetRootToNull() {
        service.initModel();

        assertNull(service.getQuestModel().getRoot());
    }

    @Test
    void initModelShouldSetCurrentStepToNull() {
        service.initModel();

        assertNull(service.getCurrentQuestStep());
    }
}
