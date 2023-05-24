package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import com.staimov.textquest.model.StepType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmptyQuestServiceTest {
    private final QuestService service =
            new EmptyQuestService(new QuestModel());

    @Test
    void initModelShouldSetRootToNull() {
        service.initModel();

        assertNull(service.getQuestRoot());
    }

    @Test
    void initModelShouldSetCountersToZero() {
        QuestStep root = new QuestStep();
        service.setQuestRoot(root);
        service.startQuest();
        service.completeQuest(StepType.NEUTRAL);

        service.initModel();

        assertAll(
                () -> assertEquals(0, service.getStartCount()),
                () -> assertEquals(0, service.getCompleteCount())
        );
    }
}
