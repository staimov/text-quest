package com.staimov.textquest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestModelTest {

    @Test
    void modelAfterRestartShouldHaveNotNullCurrentStep() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();

        assertNotNull(model.getCurrentStep());
    }

    @Test
    void modelAfterResetShouldHaveNullCurrentStep() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();
        model.reset();

        assertNull(model.getCurrentStep());
    }

    @Test
    void justCreatedModelShouldHaveNullCurrentStep() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);

        assertNull(model.getCurrentStep());
    }

    @Test
    void justCreatedModelShouldBeNotStarted() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);

        assertFalse(model.isStarted());
    }

    @Test
    void restartedModelShouldBeStarted() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);

        model.restart();

        assertTrue(model.isStarted());
    }

    @Test
    void restartModelShouldSetCurrentStepToRoot() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);

        model.restart();

        assertSame(root, model.getCurrentStep());
    }

    @Test
    void resetModelShouldSetCurrentStepToNull() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();

        model.reset();

        assertNull(model.getCurrentStep());
    }

    @Test
    void resetModelShouldNotBeStarted() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();

        model.reset();

        assertFalse(model.isStarted());
    }

    @Test
    void modelWithNullCurrentStepShouldNotBeFinal() {
        QuestModel model = new QuestModel();

        assertAll(
                () -> assertFalse(model.isFinal()),
                () -> assertFalse(model.isPositiveFinal()),
                () -> assertFalse(model.isNegativeFinal()),
                () -> assertFalse(model.isNeutralFinal())
        );
    }

    @Test
    void modelWithFinalCurrentStepShouldBeFinal() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();

        assertTrue(model.isFinal());
    }

    @Test
    void modelWithFinalCurrentStepShouldBeNeutralFinalByDefault() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();

        assertTrue(model.isNeutralFinal());
    }

    @Test
    void modelWithPositiveFinalCurrentStepShouldBePositiveFinalOnly() {
        QuestModel model = new QuestModel();
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
        QuestModel model = new QuestModel();
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
        QuestModel model = new QuestModel();
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
