package com.staimov.textquest.service;

import com.staimov.textquest.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultQuestService extends AbstractQuestService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultQuestService.class);

    public DefaultQuestService(QuestModel model, SessionData sessionData) {
        super(model, sessionData);
    }

    @Override
    public void initModel() {
        logger.debug("Quest init: {}", getClass().getSimpleName());

        synchronized (getQuestModel()) {
            clearModel();

            getQuestModel().setName("НЛО");
            getQuestModel().setDescription(
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

            addQuestStep(step0);
            addQuestStep(step1);
            addQuestStep(step2);
            addQuestStep(step3);
            addQuestStep(step4);
            addQuestStep(step5);
            addQuestStep(step6);
            setQuestRoot(step0);
        }
    }
}
