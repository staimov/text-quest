package com.staimov.textquest.config;

import com.staimov.textquest.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultQuestModelFactory implements QuestModelFactory{
    private static final Logger logger = LoggerFactory.getLogger(DefaultQuestModelFactory.class);

    @Override
    public QuestModel createModel() {
        logger.debug("Creating 'Default' quest model");

        QuestModel model = new QuestModelImpl();

        model.setName("НЛО");
        model.setDescription(
                "Ты очнулся один на терпящем бедствие космическом корабле в открытом космосе. " +
                        "Тебе как-то надо вернуться домой.");

        var step0 = new QuestStep("К счастью радиостанция работает, и ты получаешь вызов от НЛО. " +
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

        model.addStep(step0);
        model.addStep(step1);
        model.addStep(step2);
        model.addStep(step3);
        model.addStep(step4);
        model.addStep(step5);
        model.addStep(step6);
        model.setRoot(step0);

        return model;
    }
}
