package com.staimov.textquest.config;

import com.staimov.textquest.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnicornQuestModelFactory implements QuestModelFactory{
    private static final Logger logger = LoggerFactory.getLogger(UnicornQuestModelFactory.class);

    @Override
    public QuestModel createModel() {
        logger.debug("Creating 'Unicorn' quest model");

        QuestModel model = new QuestModelImpl();

        model.setName("В поисках Последнего единорога");
        model.setDescription(
                "В этой истории ты погрузишься в мир волшебства и отправишься в полное опасностей и загадок путешествие, " +
                        "чтобы спасти Последнего единорога, плененного Злым колдуном. " +
                        "В основе истории лежит история, написанная ChatGPT.");

        var step1 = new QuestStep("Ты просыпаешься на поляне, " +
                "в окружении ярких цветов и магических звуков. " +
                "Перед тобой стоит фея по имени Гламурелла, королева Фейской страны. " +
                "Она говорит, что Злой колдун похитил и пленил Последнего единорога в Фейском королевстве. " +
                "Мир волшебства под угрозой. " +
                "Твоя задача – найти единорога и освободить его. " +
                "Гламурелла предлагает тебе два пути:");

        var step2 = new QuestStep("Ты отправляешься к Волшебному лесу. " +
                "На опушке растет старое Мудрое дерево, " +
                "а в лес уходят подозрительные следы. " +
                "Теперь тебе нужно решить, как поступить:");

        var step3 = new QuestStep("Ты входишь в Древний храм, где обнаруживаешь магический артефакт " +
                "Кристалл единорога. " +
                "Но прежде чем, использовать его, тебе надо пройти испытание. Твой выбор:");

        var step4 = new QuestStep("Ты идешь по следам и приходишь ко входу в Таинственную пещеру. " +
                "Твой выбор:");

        var step5 = new QuestStep("Ты обращаешься к Мудрому дереву, оно согласно помочь, но " +
                "сначала предлагает тебе отгадать загадку. " +
                "На коре дерева появляется текст загадки: " +
                "\"Без меня нет рассвета, без меня нет заката, вечно я живу\". Твой ответ:");

        var step17 = new QuestStep("Ты дал правильный ответ, " +
                "Мудрое дерево дает тебе ценные подсказки о местонахождении логова Злого колдуна. " +
                "Твои дальнейшие действия:");

        var step18 = new QuestStep("Ты дал неправильный ответ, " +
                "Мудрое дерево предлагает тебе поискать другой путь к спасению Последнего единорога. " +
                "Твои дальнейшие действия:");

        var step6 = new QuestStep("Ты правильно отвечаешь на загадку, проходишь испытание " +
                "и получаешь силу Кристалла единорога, которая поможет тебе найти Последнего единорога. " +
                "Теперь у тебя есть средство, чтобы спасти Последнего единорога. Твой выбор:");

        var step7 = new QuestStep("Ты возвращаешься к Гламурелле и просишь ее помощи в спасении Последнего единорога. " +
                "Твой выбор:");

        var step8 = new QuestStep("Ты входишь в Таинственную пещеру и " +
                "обнаруживаешь Потайную комнату с древними свитками. Тебе предстоит решить, что делать:");

        var step14 = new QuestStep("На артефакте видны слова. Перед тобой загадка: " +
                "\"Я есть безначальное и бесконечное, но в мгновение мои цвета изменчивы. " +
                "Что я?\" Твой выбор:");

        var step13 = new QuestStep("Ты находишь логово Злого колдуна, " +
                "побеждаешь его и освобождаешь Последнего единорога, " +
                "восстанавливая мир волшебства. " +
                "Поздравляю, ты успешно завершил свою миссию, спас Последнего единорога и стал героем Фейской страны!",
                StepType.GOOD);

        var step9 = new QuestStep("Объединив усилия с феей Гламуреллой, вы находите логово Злого колдуна, " +
                "побеждаете его и освобождаете Последнего единорога, " +
                "восстанавливая мир волшебства. " +
                "Однако твои заслуги остались в тени славы королевы Гламуреллы.", StepType.NEUTRAL);

        var step10 = new QuestStep("Ты попадаешь в ловушку Злого колдуна и не смог спасти Последнего единорога. " +
                "Не расстраивайся, попробуй еще раз и найди другой путь к спасению Последнего единорога.", StepType.BAD);

        var step11 = new QuestStep("К сожалению, твой ответ неверный. Из-под земли выскакивает Злой гоблин " +
                "и закрывает тебе путь к артефакту. Как ты поступишь:");

        var step12 = new QuestStep("Ты сражаешься с врагом, но увы, не справляешься и попадаешь в плен. " +
                "Твое приключение заканчивается неудачей. " +
                "Но не расстраивайся, попробуй еще раз и найди другой путь к спасению Последнего единорога.", StepType.BAD);

        var step15 = new QuestStep("Ты обнаружил план, с помощью которого можно найти логово Злого колдуна. " +
                "Твой выбор:");

        var step16 = new QuestStep("Глубоко в конце пещеры ты находишь ценные сокровища. " +
                "Как ты поступишь:", StepType.NEUTRAL);

        var step19 = new QuestStep("Богатства ослепляют тебя, ты забираешь их, " +
                "возвращаешься с ними домой " +
                "и забываешь о первоначальной цели путешествия. Последний единорог остается в плену навечно.", StepType.NEUTRAL);

        step1.getChoices().add(new QuestChoice("Разыскать следы Последнего единорога в Волшебном лесу.",
                step2));
        step1.getChoices().add(new QuestChoice("Исследовать Древний храм в поисках магических инструментов.",
                step3));

        step2.getChoices().add(new QuestChoice("Идти по следам в лес.",
                step4));
        step2.getChoices().add(new QuestChoice("Обратиться к Мудрому дереву за подсказками.",
                step5));

        step3.getChoices().add(new QuestChoice("Пройти испытание, чтобы получить силу артефакта.",
                step14));
        step3.getChoices().add(new QuestChoice("Вернуться к Гламурелле и попросить ее помощи.",
                step7));

        step14.getChoices().add(new QuestChoice("Ответить \"Радуга\".",
                step6));
        step14.getChoices().add(new QuestChoice("Ответить \"Ветер\".",
                step11));

        step11.getChoices().add(new QuestChoice("Сразиться со Злым гоблином.",
                step12));
        step11.getChoices().add(new QuestChoice("Отступить и попросить помощи у Гламуреллы.",
                step7));

        step4.getChoices().add(new QuestChoice("Войти в Таинственную пещеру и исследовать ее глубины.",
                step8));
        step4.getChoices().add(new QuestChoice("Вернуться назад и обратиться за помощью к Гламурелле.",
                step7));

        step8.getChoices().add(new QuestChoice("Изучить свитки и найти подсказки о местонахождении Злого колдуна.",
                step15));
        step8.getChoices().add(new QuestChoice("Продолжить поиски в Таинственной пещере.",
                step16));

        step15.getChoices().add(new QuestChoice("Направиться к логову Злого колдуна, используя найденный план, " +
                "и попытаться освободить Последнего единорога.",
                step10));
        step15.getChoices().add(new QuestChoice("Вернуться назад и обратиться за помощью к Гламурелле.",
                step7));

        step5.getChoices().add(new QuestChoice("Ответить \"Луна\"",
                step18));
        step5.getChoices().add(new QuestChoice("Ответить \"Солнце\"",
                step17));
        step5.getChoices().add(new QuestChoice("Ответить \"Цветок\"",
                step18));

        step17.getChoices().add(new QuestChoice("Направиться к логову Злого колдуна " +
                "и, воспользовавшись подсказками Мудрого дерева, попытаться освободить Последнего единорога.",
                step13));
        step17.getChoices().add(new QuestChoice("Вернуться назад и обратиться за помощью к Гламурелле.",
                step7));

        step18.getChoices().add(new QuestChoice("Идти по следам в глубь Волшебного леса.",
                step4));
        step18.getChoices().add(new QuestChoice("Вернуться и исследовать Древний храм " +
                "в поисках магических инструментов.",
                step3));

        step6.getChoices().add(new QuestChoice("Вернуться назад и обратиться за помощью к Гламурелле.",
                step9));
        step6.getChoices().add(new QuestChoice("Направиться к логову Злого колдуна " +
                "и, воспользовавшись силой Кристалла единорога, найти и попытаться освободить Последнего единорога.",
                step13));

        step7.getChoices().add(new QuestChoice("Пойти вместе с Гламуреллой на поиски Злого колдуна.",
                step9));
        step7.getChoices().add(new QuestChoice("Искать подсказки и информацию о Злом колдуне самостоятельно " +
                "в Волшебном лесу.",
                step2));
        step7.getChoices().add(new QuestChoice("Искать подсказки и информацию о Злом колдуне самостоятельно " +
                "в Древнем храме.",
                step3));

        step16.getChoices().add(new QuestChoice("Забрать сокровища, сколько сможешь унести.",
                step19));
        step16.getChoices().add(new QuestChoice("Выбраться на поверхность и обратиться за советом к Гламурелле.",
                step7));

        model.addStep(step1);
        model.addStep(step2);
        model.addStep(step3);
        model.addStep(step4);
        model.addStep(step5);
        model.addStep(step6);
        model.addStep(step7);
        model.addStep(step8);
        model.addStep(step9);
        model.addStep(step10);
        model.addStep(step11);
        model.addStep(step12);
        model.addStep(step13);
        model.addStep(step14);
        model.addStep(step15);
        model.addStep(step16);
        model.addStep(step17);
        model.addStep(step18);
        model.addStep(step19);
        model.setRoot(step1);

        return model;
    }
}
