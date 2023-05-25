package com.staimov.textquest.service;

import com.staimov.textquest.model.QuestChoice;
import com.staimov.textquest.model.QuestModel;
import com.staimov.textquest.model.QuestStep;
import com.staimov.textquest.model.StepType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnicornQuestService extends AbstractQuestService {
    private static final Logger logger = LoggerFactory.getLogger(InvestigateQuestService.class);

    public UnicornQuestService(QuestModel questModel) {
        super(questModel);
    }

    @Override
    public void initModel() {
        logger.debug("Quest init: {}", getClass().getSimpleName());

        clearModel();

        getQuestModel().setName("В поисках последнего единорога");
        getQuestModel().setDescription(
                "В этой истории ты погрузишься в мир волшебства и отправишься в полное опасностей и загадок путешествие, " +
                        "чтобы спасти последнего единорога, плененного злым колдуном. " +
                        "В основе истории лежит сценарий, написанный ChatGPT.");

        var step1 = new QuestStep("Ты просыпаешься в загадочном лесу, " +
                "в окружении ярких цветов и магических звуков. " +
                "Перед тобой стоит фея по имени Люмия. " +
                "Она говорит, что злой колдун похитил и пленил последнего единорога в мире волшебства. " +
                "Твоя задача – найти единорога и освободить его. " +
                "Люмия предлагает тебе два пути:");

        var step2 = new QuestStep("Ты отправляешься в лес. На опушке растет старое дерево, " +
                "вмещающее всю мудрость волшебного леса, " +
                "а вглубь леса уходят подозрительные следы. " +
                "Теперь тебе нужно решить, как поступить:");

        var step3 = new QuestStep("Ты входишь в древний храм, где обнаруживаешь магический артефакт. " +
                "Но прежде чем, использовать его, тебе надо пройти испытание. Твой выбор:");

        var step4 = new QuestStep("Ты идешь по следам и приходишь ко входу в таинственную пещеру. " +
                "Твой выбор:");

        var step5 = new QuestStep("Ты обращаешься к мудрому дереву, " +
                "которое дает тебе подсказки о местонахождении и слабых местах злого колдуна. Твой выбор:");

        var step6 = new QuestStep("Ты правильно отвечаешь на загадку, проходишь испытание " +
                "и получаешь силу магического артефакта. " +
                "Теперь у тебя есть средство, чтобы спасти единорога. Твой выбор:");

        var step7 = new QuestStep("Ты возвращаешься к Люмии и просишь ее помощи в спасении единорога. " +
                "Твой выбор:");

        var step8 = new QuestStep("Ты входишь в таинственную пещеру и " +
                "обнаруживаешь потайную комнату с древними свитками. Тебе предстоит решить, что делать:");

        var step14 = new QuestStep("На артефакте видны слова. Перед тобой загадка: " +
                "\"Я есть безначальное и бесконечное, но в мгновение мои цвета изменчивы. " +
                "Что я?\" Твой выбор:");

        var step13 = new QuestStep("Ты находишь логово злого колдуна, " +
                "побеждаешь его и освобождаешь единорога, " +
                "восстанавливая мир волшебства. " +
                "Поздравляю, ты успешно завершил свою миссию, спас единорога и стал героем мира волшебства!",
                StepType.GOOD);

        var step9 = new QuestStep("Объединив усилия с феей Люмией, вы находите логово злого колдуна, " +
                "побеждаете его и освобождаете единорога, " +
                "восстанавливая мир волшебства. " +
                "Но ты можешь лучше )", StepType.NEUTRAL);

        var step10 = new QuestStep("Ты попадаешь в ловушку колдуна и не смог спасти единорога. " +
                "Не расстраивайся, попробуй еще раз и найди другой путь к спасению единорога.", StepType.BAD);

        var step11 = new QuestStep("К сожалению, твой ответ неверный. Из-под земли выскакивает злой гоблин " +
                "и закрывает тебе путь к артефакту. Как ты поступишь:");

        var step12 = new QuestStep("Ты сражаешься с врагом, но увы, не справляешься и попадаешь в плен. " +
                "Твое приключение заканчивается неудачей. " +
                "Но не расстраивайся, попробуй еще раз и найди другой путь к спасению единорога.", StepType.BAD);

        var step15 = new QuestStep("Ты обнаружил план, с помощью которого можно найти логово колдуна. " +
                "Твой выбор:");

        var step16 = new QuestStep("Глубоко в пещере ты находишь ценные сокровища, возвращаешься с ними домой " +
                "и забываешь о первоначальной цели путешествия. Единорог остается в плену навечно.", StepType.NEUTRAL);

        step1.getChoices().add(new QuestChoice("Разыскать следы единорога в волшебном лесу.",
                step2));
        step1.getChoices().add(new QuestChoice("Исследовать древний храм в поисках магических инструментов.",
                step3));

        step2.getChoices().add(new QuestChoice("Идти по следам.",
                step4));
        step2.getChoices().add(new QuestChoice("Обратиться к мудрому дереву за подсказками.",
                step5));

        step3.getChoices().add(new QuestChoice("Пройти испытание, чтобы получить силу артефакта.",
                step14));
        step3.getChoices().add(new QuestChoice("Вернуться к Люмии и попросить ее помощи.",
                step7));

        step14.getChoices().add(new QuestChoice("Ответить \"Радуга\".",
                step6));
        step14.getChoices().add(new QuestChoice("Ответить \"Ветер\".",
                step11));

        step11.getChoices().add(new QuestChoice("Сразиться с гоблином.",
                step12));
        step11.getChoices().add(new QuestChoice("Отступить и попросить помощи у Люмии.",
                step7));

        step4.getChoices().add(new QuestChoice("Войти в пещеру и исследовать ее глубины.",
                step8));
        step4.getChoices().add(new QuestChoice("Вернуться назад и обратиться за помощью к Люмии.",
                step7));

        step8.getChoices().add(new QuestChoice("Изучить свитки и найти подсказки о местонахождении колдуна.",
                step15));
        step8.getChoices().add(new QuestChoice("Продолжить поиски в пещере.",
                step16));

        step15.getChoices().add(new QuestChoice("Направиться к логову колдуна, используя найденный план, " +
                "и попытаться освободить единорога.",
                step10));
        step15.getChoices().add(new QuestChoice("Вернуться назад и обратиться за помощью к Люмии.",
                step7));

        step5.getChoices().add(new QuestChoice("Направиться к логову колдуна " +
                "и, воспользовавшись подсказками мудрого дерева, попытаться освободить единорога.",
                step13));
        step5.getChoices().add(new QuestChoice("Вернуться назад и обратиться за помощью к Люмии.",
                step7));

        step6.getChoices().add(new QuestChoice("Идти вместе с Элией на поиски колдуна.",
                step9));
        step6.getChoices().add(new QuestChoice("Отправиться самостоятельно на поиски колдуна.",
                step13));

        step7.getChoices().add(new QuestChoice("Пойти вместе с Элией на поиски колдуньи.",
                step9));
        step7.getChoices().add(new QuestChoice("Искать подсказки и информацию о колдуне самостоятельно " +
                "в волшебном лесу.",
                step2));
        step7.getChoices().add(new QuestChoice("Искать подсказки и информацию о колдуне самостоятельно " +
                "в древнем храме.",
                step3));

        addQuestStep(step1);
        addQuestStep(step2);
        addQuestStep(step3);
        addQuestStep(step4);
        addQuestStep(step5);
        addQuestStep(step6);
        addQuestStep(step7);
        addQuestStep(step8);
        addQuestStep(step9);
        addQuestStep(step10);
        addQuestStep(step11);
        addQuestStep(step12);
        addQuestStep(step13);
        addQuestStep(step14);
        addQuestStep(step15);
        addQuestStep(step16);
        setQuestRoot(step1);

    }
}
