package com.staimov.textquest.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuestStepTest {
    @Test
    void stepWithEmptyChoicesShouldBeFinal() {
        QuestStep step = new QuestStep();

        assertTrue(step.isFinal());
    }

    @Test
    void stepWithNotEmptyChoicesShouldNotBeFinal() {
        QuestStep step = new QuestStep();
        step.getChoices().add(new QuestChoice());

        assertFalse(step.isFinal());
    }

    @Test
    void stepWithNotEmptyChoicesShouldNotBeNeutralFinalByDefault() {
        QuestStep step = new QuestStep();
        step.getChoices().add(new QuestChoice());

        assertFalse(step.isNeutralFinal());
    }

    @Test
    void finalStepWithGoodTypeShouldBePositiveFinalOnly() {
        QuestStep step = new QuestStep();
        step.setType(StepType.GOOD);

        assertAll(
            () -> assertTrue(step.isPositiveFinal()),
            () -> assertFalse(step.isNegativeFinal()),
            () -> assertFalse(step.isNeutralFinal())
        );

    }

    @Test
    void finalStepWithBadTypeShouldBeNegativeFinalOnly() {
        QuestStep step = new QuestStep();
        step.setType(StepType.BAD);

        assertAll(
            () -> assertTrue(step.isNegativeFinal()),
            () -> assertFalse(step.isPositiveFinal()),
            () -> assertFalse(step.isNeutralFinal())
        );
    }

    @Test
    void finalStepWithNeutralTypeShouldBeNeutralFinalOnly() {
        QuestStep step = new QuestStep();
        step.setType(StepType.NEUTRAL);

        assertAll(
            () -> assertTrue(step.isNeutralFinal()),
            () -> assertFalse(step.isPositiveFinal()),
            () -> assertFalse(step.isNegativeFinal())
        );
    }

    @Test
    void stepShouldBeNeutralByDefault() {
        assertEquals(StepType.NEUTRAL, new QuestStep().getType());
    }
}
