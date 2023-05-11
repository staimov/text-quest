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
    void resetModelShouldNotBeStarted() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();
        model.reset();

        assertFalse(model.isStarted());
    }

    @Test
    void makeChoiceForNullCurrentStepShouldThrowIllegalStateException() {
        QuestModel model = new QuestModel();

        assertThrows(IllegalStateException.class,
                () -> model.makeChoice(0));
    }

    @Test
    void makeChoiceForChoiceOutOfBoundsShouldThrowIllegalStateException() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        model.restart();

        assertThrows(IndexOutOfBoundsException.class,
                () -> model.makeChoice(0));
    }

    @Test
    void makeChoiceSwitchToNextCurrentState() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep("root");
        QuestStep next = new QuestStep("next");
        QuestStep other = new QuestStep("other");
        root.getChoices().add(new QuestChoice("select other", other)); //0
        root.getChoices().add(new QuestChoice("select next", next)); //1
        model.setRoot(root);
        model.restart();
        model.makeChoice(1); //next

        assertEquals(next, model.getCurrentStep());
    }

    @Test
    void makeChoiceAssignCorrectPreviousChoiceDescription() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep("root");
        QuestStep next = new QuestStep("next");
        QuestStep other = new QuestStep("other");
        root.getChoices().add(new QuestChoice("select other", other)); //0
        root.getChoices().add(new QuestChoice("select next", next)); //1
        model.setRoot(root);
        model.restart();
        model.makeChoice(1); //next

        assertEquals(root.getChoices().get(1).getDescription(),
                model.getCurrentStep().getPreviousChoiceDescription());
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
