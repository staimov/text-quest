package com.staimov.textquest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestModelTest {
    private final QuestModel model = new QuestModel();

    @Test
    void modelAfterRestartShouldHaveNotNullCurrentStep() {
        QuestStep root = new QuestStep();
        model.setRoot(root);

        model.restart();

        assertNotNull(model.getCurrentStep());
    }

    @Test
    void modelAfterResetShouldHaveNullCurrentStep() {
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();

        model.reset();

        assertNull(model.getCurrentStep());
    }

    @Test
    void justCreatedModelShouldHaveNullCurrentStep() {
        QuestStep root = new QuestStep();
        model.setRoot(root);

        assertNull(model.getCurrentStep());
    }

    @Test
    void justCreatedModelShouldBeNotStarted() {
        QuestStep root = new QuestStep();
        model.setRoot(root);

        assertFalse(model.isStarted());
    }

    @Test
    void restartedModelShouldBeStarted() {
        QuestStep root = new QuestStep();
        model.setRoot(root);

        model.restart();

        assertTrue(model.isStarted());
    }

    @Test
    void restartModelShouldSetCurrentStepToRoot() {
        QuestStep root = new QuestStep();
        model.setRoot(root);

        model.restart();

        assertSame(root, model.getCurrentStep());
    }

    @Test
    void resetModelShouldSetCurrentStepToNull() {
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();

        model.reset();

        assertNull(model.getCurrentStep());
    }

    @Test
    void resetModelShouldNotBeStarted() {
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();

        model.reset();

        assertFalse(model.isStarted());
    }

    @Test
    void modelWithNullCurrentStepShouldNotBeFinal() {
        assertAll(
                () -> assertFalse(model.isFinal()),
                () -> assertFalse(model.isPositiveFinal()),
                () -> assertFalse(model.isNegativeFinal()),
                () -> assertFalse(model.isNeutralFinal())
        );
    }

    @Test
    void modelWithFinalCurrentStepShouldBeFinal() {
        QuestStep root = new QuestStep();
        model.setRoot(root);

        model.restart();

        assertTrue(model.isFinal());
    }

    @Test
    void modelWithFinalCurrentStepShouldBeNeutralFinalByDefault() {
        QuestStep root = new QuestStep();
        model.setRoot(root);

        model.restart();

        assertTrue(model.isNeutralFinal());
    }

    @Test
    void modelWithPositiveFinalCurrentStepShouldBePositiveFinalOnly() {
        QuestStep root = new QuestStep();
        root.setType(StepType.GOOD);
        model.setRoot(root);

        model.restart();

        assertAll(
                () -> assertTrue(model.isPositiveFinal()),
                () -> assertFalse(model.isNegativeFinal()),
                () -> assertFalse(model.isNeutralFinal())
        );
    }

    @Test
    void modelWithNegativeFinalCurrentStepShouldBeNegativeFinalOnly() {
        QuestStep root = new QuestStep();
        root.setType(StepType.BAD);
        model.setRoot(root);

        model.restart();

        assertAll(
                () -> assertFalse(model.isPositiveFinal()),
                () -> assertTrue(model.isNegativeFinal()),
                () -> assertFalse(model.isNeutralFinal())
        );
    }

    @Test
    void modelWithNeutralFinalCurrentStepShouldBeNeutralFinalOnly() {
        QuestStep root = new QuestStep();
        root.setType(StepType.NEUTRAL);
        model.setRoot(root);

        model.restart();

        assertAll(
                () -> assertFalse(model.isPositiveFinal()),
                () -> assertFalse(model.isNegativeFinal()),
                () -> assertTrue(model.isNeutralFinal())
        );
    }
}
