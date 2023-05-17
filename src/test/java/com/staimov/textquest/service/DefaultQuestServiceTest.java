package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultQuestServiceTest {
    @Test
    void makeQuestChoiceForNotStartedQuestShouldThrowIllegalStateException() {
        QuestService service = new DefaultQuestService(new QuestModel());

        assertThrows(IllegalStateException.class,
                () -> service.makeQuestChoice(0));
    }

    @Test
    void makeQuestChoiceForChoiceOutOfBoundsShouldThrowIndexOutOfBoundsException() {
        QuestModel model = new QuestModel();
        model.setRoot(new QuestStep());
        QuestService service = new DefaultQuestService(model);
        service.restartQuest();

        assertThrows(IndexOutOfBoundsException.class,
                () -> service.makeQuestChoice(0));
    }

    @Test
    void makeQuestChoiceShouldSwitchToRelevantNextStep() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep("root");
        QuestStep next = new QuestStep("next");
        QuestStep other = new QuestStep("other");
        root.getChoices().add(new QuestChoice("select other", other)); //0
        root.getChoices().add(new QuestChoice("select next", next)); //1
        model.setRoot(root);
        QuestService service = new DefaultQuestService(model);
        service.restartQuest();

        service.makeQuestChoice(1); //next

        assertEquals(next, service.getCurentQuestStep());
    }

    @Test
    void makeQuestChoiceShouldAssignCorrectPreviousChoiceDescription() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep("root");
        QuestStep next = new QuestStep("next");
        QuestStep other = new QuestStep("other");
        root.getChoices().add(new QuestChoice("select other", other)); //0
        root.getChoices().add(new QuestChoice("select next", next)); //1
        model.setRoot(root);
        QuestService service = new DefaultQuestService(model);
        service.restartQuest();

        service.makeQuestChoice(1); //next

        assertEquals(root.getChoices().get(1).getDescription(),
                service.getCurentQuestStep().getPreviousChoiceDescription());
    }

    @Test
    void restartQuestShouldSetCurrentStepToRoot() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        QuestService service = new DefaultQuestService(model);

        service.restartQuest();

        assertSame(root, service.getCurentQuestStep());
    }

    @Test
    void resetQuestShouldSetCurrentStepToNull() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        QuestService service = new DefaultQuestService(model);
        service.restartQuest();

        service.resetQuest();

        assertNull(service.getCurentQuestStep());
    }

    @Test
    void restartQuestShouldMakeModelStarted() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        QuestService service = new DefaultQuestService(model);

        service.restartQuest();

        assertTrue(service.getQuestModel().isStarted());
    }

    @Test
    void resetQuestShouldMakeModelNotStarted() {
        QuestModel model = new QuestModel();
        QuestStep root = new QuestStep();
        model.setRoot(root);
        QuestService service = new DefaultQuestService(model);
        service.restartQuest();

        service.resetQuest();

        assertFalse(service.getQuestModel().isStarted());
    }

    @Test
    void initModelShouldAddRootWithChoices() {
        QuestModel model = new QuestModel();
        QuestService service = new DefaultQuestService(model);

        service.initModel();

        assertAll(
                () -> assertNotNull(service.getQuestModel().getRoot()),
                () -> assertTrue(service.getQuestModel().getRoot().getChoices().size() > 0),
                () -> {
                    for (QuestChoice choice: service.getQuestModel().getRoot().getChoices()) {
                        assertNotNull(choice.getNextStep());
                    }
                }
        );
    }

    @Test
    void initModelShouldSetCurrentStepToNull() {
        QuestModel model = new QuestModel();
        QuestService service = new DefaultQuestService(model);

        service.initModel();

        assertNull(service.getCurentQuestStep());
    }
}
