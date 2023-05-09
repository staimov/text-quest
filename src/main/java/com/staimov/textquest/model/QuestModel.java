package com.staimov.textquest.model;

import org.springframework.stereotype.Component;

@Component
public class QuestModel {
    private QuestStep root;
    private QuestStep currentStep;
    private String name;
    private String description;
    private String heroName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void restart() {
        currentStep = root;
    }

    public void reset() {
        currentStep = null;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public boolean isInProgress() {
        return currentStep != null;
    }

    public QuestStep getRoot() {
        return root;
    }

    public void setRoot(QuestStep root) {
        this.root = root;
    }

    public QuestStep getCurrentStep() {
        return currentStep;
    }

    public void makeChoice(int choiceIndex) {
        if (currentStep == null) {
            throw new IllegalStateException("No current step.");
        }

        if (choiceIndex >= currentStep.getChoices().size()) {
            throw new IndexOutOfBoundsException();
        }

        QuestChoice choiceMade = currentStep.getChoices().get(choiceIndex);
        currentStep = choiceMade.getNextStep();
        currentStep.setPreviousChoiceDescription(choiceMade.getDescription());
    }

    public boolean isFinal() {
        return currentStep.isFinal();
    }

    public boolean isPositiveFinal() {
        return currentStep.isPositiveFinal();
    }

    public boolean isNegativeFinal() {
        return currentStep.isNegativeFinal();
    }

    public boolean isNeutralFinal() {
        return currentStep.isNeutralFinal();
    }
}
