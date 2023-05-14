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

    final private QuestModel model;

    @Autowired
    public QuestService(QuestModel model) {
        this.model = model;
    }

    public void initModel() {
        model.setName("Контакт");
        model.setDescription(
                "Ты очнулся один на терпящем бедствие космическом корабле в открытом космосе. " +
                "Тебе как-то надо вернуться домой.");

        var step0 = new QuestStep("Радиостанция работает, и ты получаешь вызов от НЛО. " +
                "Приянять вызов?");
        var step1 = new QuestStep("Ты принял вызов. " +
                "Неопознанный корабль пристыковался к твоему кораблю. " +
                "Поднимаешься на мостик к капитану?", StepType.NEUTRAL);
        var step2 = new QuestStep("Ты поднялся на мостик. " +
                "Ты кто?", StepType.NEUTRAL);
        var step3 = new QuestStep("Тебя вернули домой.", StepType.GOOD);
        var step4 = new QuestStep("Ты отклонил вызов и теперь не вернешься домой.", StepType.BAD);
        var step5 = new QuestStep("Ты не пошел на переговоры и теперь не вернешься домой.", StepType.BAD);
        var step6 = new QuestStep("Твою ложь разоблачили, а тебя вернули на твой корабль. " +
                "Теперь ты не вернешься домой.", StepType.BAD);

        step0.getChoices().add(new QuestChoice("Принять вызов", step1));
        step0.getChoices().add(new QuestChoice("Отклонить вызов", step4));

        step1.getChoices().add(new QuestChoice("Подняться на мостик", step2));
        step1.getChoices().add(new QuestChoice("Отказаться подниматься на мостик", step5));

        step2.getChoices().add(new QuestChoice("Рассказать правду о себе", step3));
        step2.getChoices().add(new QuestChoice("Солгать о себе", step6));

        model.setRoot(step0);
    }

    public QuestModel getQuestModel() {
        return model;
    }

    public void restartQuest() {
        model.restart();
        logger.info("Quest started: {}", model.getName());
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
