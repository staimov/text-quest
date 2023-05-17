package com.staimov.textquest.model;

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

    public QuestStep getRoot() {
        return root;
    }

    public void setRoot(QuestStep root) {
        this.root = root;
    }

    public QuestStep getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(QuestStep currentStep) {
        this.currentStep = currentStep;
    }

    public boolean isFinal() {
        return isStarted() && currentStep.isFinal();
    }

    public boolean isPositiveFinal() {
        return isStarted() && currentStep.isPositiveFinal();
    }

    public boolean isNegativeFinal() {
        return isStarted() && currentStep.isNegativeFinal();
    }

    public boolean isNeutralFinal() {
        return isStarted() && currentStep.isNeutralFinal();
    }

    public boolean isStarted() {
        return currentStep != null;
    }
}
