package com.staimov.textquest.service;

import com.staimov.textquest.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestServiceTest {
    @Test
    void constructorWithModelNullArgumentShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuestServiceImpl(null, new SessionData()));
    }

    @Test
    void constructorWithSessionDataNullArgumentShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new QuestServiceImpl(new QuestModelImpl(), null));
    }

    @Test
    void addedQuestStepIdShouldBeContainedInTheService() {
        QuestModel model = new QuestModelImpl("test");
        QuestStep step = new QuestStep();
        model.addStep(step);
        QuestService service =
                new QuestServiceImpl(
                        model,
                        new SessionData());

        assertTrue(service.containsQuestStep(step.getId()));
    }

    @Test
    void addedQuestStepShouldBeGettableFromTheService() {
        QuestModel model = new QuestModelImpl("test");
        QuestStep step = new QuestStep();
        model.addStep(step);
        QuestService service =
                new QuestServiceImpl(
                        model,
                        new SessionData());

        assertSame(step, service.getQuestStep(step.getId()));
    }

    @Test
    void getNextQuestStepWithInvalidStepIdArgShouldThrowObjectNotFoundException() {
        QuestService service =
                new QuestServiceImpl(
                        new QuestModelImpl("test"),
                        new SessionData());

        assertThrows(ObjectNotFoundException.class,
                () -> service.getNextQuestStep(1, 1));
    }

    @Test
    void getNextQuestStepWithInvalidChoiceIdArgShouldThrowObjectNotFoundException() {
        QuestModel model = new QuestModelImpl("test");
        QuestStep step = new QuestStep();
        model.addStep(step);
        QuestService service =
                new QuestServiceImpl(
                        model,
                        new SessionData());

        assertThrows(IndexOutOfBoundsException.class,
                () -> service.getNextQuestStep(step.getId(), 1));
    }

    @Test
    void getNextQuestStepWithValidArgsShouldReturnValidStepObject() {
        QuestModel model = new QuestModelImpl("test");
        QuestStep root = new QuestStep();
        QuestStep next = new QuestStep();
        root.getChoices().add(new QuestChoice("go next", next)); // 0
        model.addStep(root);
        model.addStep(next);
        QuestService service =
                new QuestServiceImpl(
                        model,
                        new SessionData());

        assertSame(next, service.getNextQuestStep(root.getId(), 0));
    }

    @Test
    void getChoiceDescriptionWithInvalidStepIdArgShouldThrowObjectNotFoundException() {
        QuestService service =
                new QuestServiceImpl(
                        new QuestModelImpl("test"),
                        new SessionData());

        assertThrows(ObjectNotFoundException.class,
                () -> service.getChoiceDescription(1, 1));
    }

    @Test
    void getChoiceDescriptionWithInvalidChoiceIdArgShouldThrowObjectNotFoundException() {
        QuestModel model = new QuestModelImpl("test");
        QuestStep step = new QuestStep();
        model.addStep(step);
        QuestService service =
                new QuestServiceImpl(
                        model,
                        new SessionData());

        assertThrows(IndexOutOfBoundsException.class,
                () -> service.getChoiceDescription(step.getId(), 1));
    }

    @Test
    void getChoiceDescriptionWithValidArgsShouldReturnValidString() {
        QuestModel model = new QuestModelImpl("test");
        QuestStep root = new QuestStep();
        QuestStep next = new QuestStep();
        root.getChoices().add(new QuestChoice("go next", next)); // 0
        model.addStep(root);
        model.addStep(next);
        QuestService service =
                new QuestServiceImpl(
                        model,
                        new SessionData());

        assertEquals("go next", service.getChoiceDescription(root.getId(), 0));
    }

    @Test
    void notAddedQuestStepIdShouldNotBeContainedInTheService() {
        QuestService service =
                new QuestServiceImpl(
                        new QuestModelImpl("test"),
                        new SessionData());

        assertFalse(service.containsQuestStep(789));
    }

    @Test
    void getQuestStepShouldReturnNullIfStepIdIsNotInTheService() {
        QuestService service =
                new QuestServiceImpl(
                        new QuestModelImpl("test"),
                        new SessionData());

        assertNull(service.getQuestStep(789));
    }

    @Test
    void startQuestShouldIncrementStartCount() {
        QuestService service =
                new QuestServiceImpl(
                        new QuestModelImpl("test"),
                        new SessionData());
        int prevStartCount = service.getStartCount();

        service.startQuest();

        assertEquals(prevStartCount + 1, service.getStartCount());
    }

    @Test
    void completeQuestShouldIncrementCompleteCount() {
        QuestService service =
                new QuestServiceImpl(
                        new QuestModelImpl("test"),
                        new SessionData());
        int prevCompleteCount = service.getCompleteCount();

        service.completeQuest(StepType.NEUTRAL);

        assertEquals(prevCompleteCount + 1, service.getCompleteCount());
    }

    @Test
    void justCreatedModelShouldHaveCountersToZero() {
        QuestService service =
                new QuestServiceImpl(
                        new QuestModelImpl("test"),
                        new SessionData());

        assertAll(
                () -> assertEquals(0, service.getStartCount()),
                () -> assertEquals(0, service.getCompleteCount())
        );
    }
}
