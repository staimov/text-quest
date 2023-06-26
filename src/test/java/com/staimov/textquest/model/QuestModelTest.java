package com.staimov.textquest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestModelTest {
    private final QuestModel model = new QuestModelImpl("test");

    @Test
    void addedStepIdShouldBeContainedInTheModel() {
        QuestStep step = new QuestStep();

        model.addStep(step);

        assertTrue(model.containsStep(step.getId()));
    }

    @Test
    void notAddedStepIdShouldNotBeContainedInTheModel() {
        assertFalse(model.containsStep(789));
    }

    @Test
    void addedStepShouldBeGettableFromTheModel() {
        QuestStep step = new QuestStep();

        model.addStep(step);

        assertSame(step, model.getStep(step.getId()));
    }

    @Test
    void getStepShouldReturnNullIfStepIdIsNotInTheModel() {
        assertNull(model.getStep(789));
    }

    @Test
    void setRootShouldSetRoot() {
        QuestStep step = new QuestStep();

        model.setRoot(step);

        assertSame(step, model.getRoot());
    }
}
