package com.staimov.textquest.model;

public class QuestChoice {
    private String description;
    private QuestStep nextStep;

    public QuestChoice() {
    }

    public QuestChoice(String description, QuestStep nextStep) {
        this.description = description;
        this.nextStep = nextStep;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuestStep getNextStep() {
        return nextStep;
    }

    public void setNextStep(QuestStep nextStep) {
        this.nextStep = nextStep;
    }
}
