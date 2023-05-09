package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import com.staimov.textquest.model.StepType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestService {
    private static final Logger logger = LoggerFactory.getLogger(QuestService.class);

    private QuestModel model;

    @Autowired
    public QuestService(QuestModel model) {
        this.model = model;
    }

    public void initModel() {
        model.setName("Куда пойдешь?");
        model.setDescription("Квест о выборе пути.");

        QuestStep root = new QuestStep("Старт. Куда пойдешь?");

        QuestStep rightFinal = new QuestStep("Счастье нашел.", StepType.GOOD);
        QuestStep centerFinal = new QuestStep("Коня потерял.", StepType.NEUTRAL);
        QuestStep leftFinal = new QuestStep("Голову потерял.", StepType.BAD);

        root.getChoices().add(new QuestChoice("Налево иду", leftFinal));
        root.getChoices().add(new QuestChoice("Прямо иду", centerFinal));
        root.getChoices().add(new QuestChoice("Направо иду", rightFinal));

        model.setRoot(root);
    }

    public QuestModel getQuestModel() {
        return model;
    }

    public void restartQuest() {
        model.restart();
        logger.info("Quest started");
    }

    public void resetQuest() {
        model.reset();
    }

    public void nextQuestStep(int choiceId) {
        model.makeChoice(choiceId);
    }

    public QuestStep getCurentQuestStep() {
        return model.getCurrentStep();
    }
}
