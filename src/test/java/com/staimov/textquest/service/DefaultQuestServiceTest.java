package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultQuestServiceTest {
    private final QuestService service =
            new DefaultQuestService(new QuestModel());

    @Test
    void makeQuestChoiceForNotStartedQuestShouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class,
                () -> service.makeQuestChoice(0));
    }

    @Test
    void makeQuestChoiceForNotStartedQuestShouldExceptionWithRelevantMessage() {
        String actualMessage = null;

        try {
            service.makeQuestChoice(0);
        }
        catch (Exception e) {
            actualMessage = e.getMessage();
        }

        assertEquals("Current step is null", actualMessage);
    }

    @Test
    void makeQuestChoiceForChoiceOutOfBoundsShouldThrowIndexOutOfBoundsException() {
        service.setQuestRoot(new QuestStep());
        service.restartQuest();

        assertThrows(IndexOutOfBoundsException.class,
                () -> service.makeQuestChoice(0));
    }

    @Test
    void makeQuestChoiceForChoiceOutOfBoundsShouldThrowExceptionWithRelevantMessage() {
        service.setQuestRoot(new QuestStep());
        service.restartQuest();
        int index = 2;
        String expectedMessage  = String.format("Choice id %d is out of bounds", index);
        String actualMessage = null;

        try {
            service.makeQuestChoice(index);
        }
        catch (Exception e) {
            actualMessage = e.getMessage();
        }

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void makeQuestChoiceShouldSwitchToRelevantNextStep() {
        QuestStep root = new QuestStep("root");
        QuestStep next = new QuestStep("next");
        QuestStep other = new QuestStep("other");
        root.getChoices().add(new QuestChoice("select other", other)); //0
        root.getChoices().add(new QuestChoice("select next", next)); //1
        service.setQuestRoot(root);
        service.restartQuest();

        service.makeQuestChoice(1); //next

        assertSame(next, service.getCurrentQuestStep());
    }

    @Test
    void makeQuestChoiceShouldAssignCorrectPreviousChoiceDescription() {
        QuestStep root = new QuestStep("root");
        QuestStep next = new QuestStep("next");
        QuestStep other = new QuestStep("other");
        root.getChoices().add(new QuestChoice("select other", other)); //0
        root.getChoices().add(new QuestChoice("select next", next)); //1
        service.setQuestRoot(root);
        service.restartQuest();

        service.makeQuestChoice(1); //next

        assertEquals(root.getChoices().get(1).getDescription(),
                service.getCurrentQuestStep().getPreviousChoiceDescription());
    }

    @Test
    void restartQuestShouldSetCurrentStepToRoot() {
        QuestStep root = new QuestStep();
        service.setQuestRoot(root);

        service.restartQuest();

        assertSame(root, service.getCurrentQuestStep());
    }

    @Test
    void resetQuestShouldSetCurrentStepToNull() {
        QuestStep root = new QuestStep();
        service.setQuestRoot(root);
        service.restartQuest();

        service.resetQuest();

        assertNull(service.getCurrentQuestStep());
    }

    @Test
    void restartQuestShouldMakeQuestStarted() {
        QuestStep root = new QuestStep();
        service.setQuestRoot(root);

        service.restartQuest();

        assertTrue(service.isQuestStarted());
    }

    @Test
    void resetQuestShouldMakeQuestNotStarted() {
        QuestStep root = new QuestStep();
        service.setQuestRoot(root);
        service.restartQuest();

        service.resetQuest();

        assertFalse(service.isQuestStarted());
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
    void initModelShouldSetCurrentStepToNull() {
        service.initModel();

        assertNull(service.getCurrentQuestStep());
    }

    @Test
    void restartQuestShouldIncrementStartCount() {
        int prevStartCount = service.getStartCount();

        service.restartQuest();

        assertEquals(prevStartCount + 1, service.getStartCount());
    }

    @Test
    void restartOneStepQuestShouldIncrementCompleteCount() {
        QuestStep root = new QuestStep();
        service.setQuestRoot(root);
        int prevCompleteCount = service.getCompleteCount();

        service.restartQuest();

        assertEquals(prevCompleteCount + 1, service.getCompleteCount());
    }

    @Test
    void completeQuestShouldIncrementCompleteCount() {
        QuestStep root = new QuestStep("root");
        QuestStep next = new QuestStep("next");
        root.getChoices().add(new QuestChoice("select next", next));
        service.setQuestRoot(root);
        int prevCompleteCount = service.getCompleteCount();
        service.restartQuest();

        service.makeQuestChoice(0);

        assertEquals(prevCompleteCount + 1, service.getCompleteCount());
    }
}
