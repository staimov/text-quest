package com.staimov.textquest.model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestModeOrderedTest {

    private final QuestModel model = new QuestModel();

    @Test
    @Order(10)
    void justCreatedModelShouldHaveNullCurrentStep() {
        QuestStep root = new QuestStep("root");
        model.setRoot(root);

        assertNull(model.getCurrentStep());
    }

    @Test
    @Order(20)
    void justCreatedModelShouldBeNotStarted() {
        assertFalse(model.isStarted());
    }

    @Test
    @Order(30)
    void modelWithNullCurrentStepShouldNotBeFinal() {
        assertAll(
                () -> assertFalse(model.isFinal()),
                () -> assertFalse(model.isPositiveFinal()),
                () -> assertFalse(model.isNegativeFinal()),
                () -> assertFalse(model.isNeutralFinal())
        );
    }

    @Test
    @Order(40)
    void makeChoiceForNullCurrentStepShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class,
                () -> model.makeChoice(0));
    }

    @Test
    @Order(50)
    void restartedModelShouldBeStarted() {
        model.restart();

        assertTrue(model.isStarted());
    }

    @Test
    @Order(60)
    void modelAfterRestartShouldHaveNotNullCurrentStep() {
        assertNotNull(model.getCurrentStep());
    }

    @Test
    @Order(70)
    void modelWithFinalCurrentStepShouldBeFinal() {
        assertTrue(model.isFinal());
    }

    @Test
    @Order(80)
    void modelWithFinalCurrentStepShouldBeNeutralFinalByDefault() {
        assertTrue(model.isNeutralFinal());
    }

    @Test
    @Order(90)
    void modelWithPositiveFinalCurrentStepShouldBePositiveFinalOnly() {
        model.getCurrentStep().setType(StepType.GOOD);

        assertAll(
                () -> assertTrue(model.isPositiveFinal()),
                () -> assertFalse(model.isNegativeFinal()),
                () -> assertFalse(model.isNeutralFinal())
        );
    }

    @Test
    @Order(100)
    void modelWithNegativeFinalCurrentStepShouldBeNegativeFinalOnly() {
        model.getCurrentStep().setType(StepType.BAD);

        assertAll(
                () -> assertFalse(model.isPositiveFinal()),
                () -> assertTrue(model.isNegativeFinal()),
                () -> assertFalse(model.isNeutralFinal())
        );
    }

    @Test
    @Order(110)
    void modelWithNeutralFinalCurrentStepShouldBeNeutralFinalOnly() {
        model.getCurrentStep().setType(StepType.NEUTRAL);

        assertAll(
                () -> assertFalse(model.isPositiveFinal()),
                () -> assertFalse(model.isNegativeFinal()),
                () -> assertTrue(model.isNeutralFinal())
        );
    }

    @Test
    @Order(120)
    void makeChoiceForChoiceOutOfBoundsShouldThrowIllegalStateException() {
        assertThrows(IndexOutOfBoundsException.class,
                () -> model.makeChoice(0));
    }

    @Test
    @Order(130)
    void makeChoiceSwitchToNextCurrentState() {
        QuestStep next = new QuestStep("next");
        QuestStep other = new QuestStep("other");
        model.getRoot().getChoices().add(new QuestChoice("select other", other)); //0
        model.getRoot().getChoices().add(new QuestChoice("select next", next)); //1
        model.makeChoice(1); //next

        assertEquals(next, model.getCurrentStep());
    }

    @Test
    @Order(140)
    void makeChoiceAssignCorrectPreviousChoiceDescription() {
        assertEquals(model.getRoot().getChoices().get(1).getDescription(),
                model.getCurrentStep().getPreviousChoiceDescription());
    }

    @Test
    @Order(150)
    void modelAfterResetShouldHaveNullCurrentStep() {
        model.reset();

        assertNull(model.getCurrentStep());
    }

    @Test
    @Order(160)
    void resetModelShouldNotBeStarted() {
        assertFalse(model.isStarted());
    }
}
