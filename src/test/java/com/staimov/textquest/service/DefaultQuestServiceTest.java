package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import com.staimov.textquest.model.StepType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultQuestServiceTest {
    private final QuestService service =
            new DefaultQuestService(new QuestModel("test"));

    @Test
    void constructorWithNullArgumentShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new DefaultQuestService(null));
    }

    @Test
    void getNextQuestStepWithInvalidStepIdArgShouldThrowObjectNotFoundException() {
        assertThrows(ObjectNotFoundException.class,
                () -> service.getNextQuestStep(1, 1));
    }

    @Test
    void getNextQuestStepWithInvalidChoiceIdArgShouldThrowObjectNotFoundException() {
        QuestStep step = new QuestStep();
        service.addQuestStep(step);

        assertThrows(IndexOutOfBoundsException.class,
                () -> service.getNextQuestStep(step.getId(), 1));
    }

    @Test
    void getNextQuestStepWithValidArgsShouldReturnValidStepObject() {
        QuestStep root = new QuestStep();
        QuestStep next = new QuestStep();
        root.getChoices().add(new QuestChoice("go next", next)); // 0
        service.addQuestStep(root);
        service.addQuestStep(next);

        assertSame(next, service.getNextQuestStep(root.getId(), 0));
    }

    @Test
    void getChoiceDescriptionWithInvalidStepIdArgShouldThrowObjectNotFoundException() {
        assertThrows(ObjectNotFoundException.class,
                () -> service.getChoiceDescription(1, 1));
    }

    @Test
    void getChoiceDescriptionWithInvalidChoiceIdArgShouldThrowObjectNotFoundException() {
        QuestStep step = new QuestStep();
        service.addQuestStep(step);

        assertThrows(IndexOutOfBoundsException.class,
                () -> service.getChoiceDescription(step.getId(), 1));
    }

    @Test
    void getChoiceDescriptionWithValidArgsShouldReturnValidString() {
        QuestStep root = new QuestStep();
        QuestStep next = new QuestStep();
        root.getChoices().add(new QuestChoice("go next", next)); // 0
        service.addQuestStep(root);
        service.addQuestStep(next);

        assertEquals("go next", service.getChoiceDescription(root.getId(), 0));
    }

    @Test
    void getQuestStep() {

    }

    @Test
    void addedQuestStepIdShouldBeContainedInTheService() {
        QuestStep step = new QuestStep();

        service.addQuestStep(step);

        assertTrue(service.containsQuestStep(step.getId()));
    }

    @Test
    void notAddedQuestStepIdShouldNotBeContainedInTheService() {
        assertFalse(service.containsQuestStep(789));
    }

    @Test
    void addedQuestStepShouldBeGettableFromTheService() {
        QuestStep step = new QuestStep();

        service.addQuestStep(step);

        assertSame(step, service.getQuestStep(step.getId()));
    }

    @Test
    void getQuestStepShouldReturnNullIfStepIdIsNotInTheService() {
        assertNull(service.getQuestStep(789));
    }

    @Test
    void setQuestRootShouldSetTheRoot() {
        QuestStep step = new QuestStep();

        service.setQuestRoot(step);

        assertSame(step, service.getQuestRoot());
    }

    @Test
    void clearModelShouldClearStepsAndRootAndName() {
        QuestStep step = new QuestStep();
        service.addQuestStep(step);
        service.setQuestRoot(step);

        service.clearModel();

        assertAll(
                () -> assertNull(service.getQuestRoot()),
                () -> assertNull(service.getQuestStep(step.getId())),
                () -> assertNull(service.getQuestName())
        );
    }

    @Test
    void initModelShouldAddRootWithChoices() {
        service.initModel();

        assertAll(
                () -> assertNotNull(service.getQuestRoot()),
                () -> assertTrue(service.getQuestRoot().getChoices().size() > 0),
                () -> {
                    for (QuestChoice choice: service.getQuestRoot().getChoices()) {
                        assertNotNull(choice.getNextStep());
                    }
                }
        );
    }

    @Test
    void startQuestShouldIncrementStartCount() {
        int prevStartCount = service.getStartCount();

        service.startQuest();

        assertEquals(prevStartCount + 1, service.getStartCount());
    }

    @Test
    void completeQuestShouldIncrementCompleteCount() {
        int prevCompleteCount = service.getCompleteCount();

        service.completeQuest(StepType.NEUTRAL);

        assertEquals(prevCompleteCount + 1, service.getCompleteCount());
    }

    @Test
    void resetCountersShouldSetCountersToZero() {
        QuestStep root = new QuestStep();
        service.setQuestRoot(root);
        service.startQuest();
        service.completeQuest(StepType.NEUTRAL);

        service.resetCounters();

        assertAll(
                () -> assertEquals(0, service.getStartCount()),
                () -> assertEquals(0, service.getCompleteCount())
        );
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
