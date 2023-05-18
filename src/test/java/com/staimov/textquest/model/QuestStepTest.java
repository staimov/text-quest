package com.staimov.textquest.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestStepTest {
    private final QuestStep step = new QuestStep();

    @Test
    void stepWithEmptyChoicesShouldBeFinal() {
        assertTrue(step.isFinal());
    }

    @Test
    void stepWithNotEmptyChoicesShouldNotBeFinal() {
        step.getChoices().add(new QuestChoice());

        assertFalse(step.isFinal());
    }

    @Test
    void stepWithNotEmptyChoicesShouldNotBeNeutralFinalByDefault() {
        step.getChoices().add(new QuestChoice());

        assertFalse(step.isNeutralFinal());
    }

    @Test
    void finalStepWithGoodTypeShouldBePositiveFinalOnly() {
        step.setType(StepType.GOOD);

        assertAll(
            () -> assertTrue(step.isPositiveFinal()),
            () -> assertFalse(step.isNegativeFinal()),
            () -> assertFalse(step.isNeutralFinal())
        );

    }

    @Test
    void finalStepWithBadTypeShouldBeNegativeFinalOnly() {
        step.setType(StepType.BAD);

        assertAll(
            () -> assertTrue(step.isNegativeFinal()),
            () -> assertFalse(step.isPositiveFinal()),
            () -> assertFalse(step.isNeutralFinal())
        );
    }

    @Test
    void finalStepWithNeutralTypeShouldBeNeutralFinalOnly() {
        step.setType(StepType.NEUTRAL);

        assertAll(
            () -> assertTrue(step.isNeutralFinal()),
            () -> assertFalse(step.isPositiveFinal()),
            () -> assertFalse(step.isNegativeFinal())
        );
    }

    @Test
    void stepShouldBeNeutralByDefault() {
        assertEquals(StepType.NEUTRAL, step.getType());
    }

    @Test
    void newStepsShouldHaveAutoincrementedIds() {
        List<QuestStep> steps = new ArrayList<>();

        for (int i = 0; i < 10; ++i) {
            steps.add(new QuestStep());
        }

        long firstId = steps.get(0).getId();

        for (int i = 0; i < 10; ++i) {
            assertEquals(firstId + i, steps.get(i).getId());
        }
    }
}
