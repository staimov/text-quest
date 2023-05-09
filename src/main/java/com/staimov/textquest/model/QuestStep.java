package com.staimov.textquest.model;

import java.util.ArrayList;
import java.util.List;

public class QuestStep {
    private String description;
    private String previousChoiceDescription;
    private StepType type = StepType.NEUTRAL;
    private List<QuestChoice> choices = new ArrayList<>();

    public QuestStep() {
    }

    public QuestStep(String description) {
        this.description = description;
    }

    public QuestStep(String description, StepType type) {
        this.description = description;
        this.type = type;
    }

    public StepType getType() {
        return type;
    }

    public void setType(StepType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<QuestChoice> getChoices() {
        return choices;
    }

    public String getPreviousChoiceDescription() {
        return previousChoiceDescription;
    }

    public void setPreviousChoiceDescription(String previousChoiceDescription) {
        this.previousChoiceDescription = previousChoiceDescription;
    }

    public boolean isFinal() {
        return choices.isEmpty();
    }

    public boolean isPositiveFinal() {
        return isFinal() && getType() == StepType.GOOD;
    }

    public boolean isNegativeFinal() {
        return isFinal() && getType() == StepType.BAD;
    }

    public boolean isNeutralFinal() {
        return isFinal() && getType() == StepType.NEUTRAL;
    }

}
