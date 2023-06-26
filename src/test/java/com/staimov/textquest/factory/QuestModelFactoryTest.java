package com.staimov.textquest.factory;

import com.staimov.textquest.config.DefaultQuestModelFactory;
import com.staimov.textquest.config.QuestModelFactory;
import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestModelFactoryTest {
    private final QuestModelFactory factory = new DefaultQuestModelFactory();

    @Test
    void createModelShouldReturnModelObject() {
        QuestModel model = factory.createModel();

        assertNotNull(model);
    }

    @Test
    void createModelShouldAddRootWithChoices() {
        QuestModel model = factory.createModel();

        assertAll(
                () -> assertNotNull(model.getRoot()),
                () -> assertTrue(model.getRoot().getChoices().size() > 0),
                () -> {
                    for (QuestChoice choice: model.getRoot().getChoices()) {
                        assertNotNull(choice.getNextStep());
                    }
                }
        );
    }
}
