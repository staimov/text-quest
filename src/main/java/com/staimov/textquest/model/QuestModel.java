package com.staimov.textquest.model;

public interface QuestModel {
    QuestStep getStep(long id);

    boolean containsStep(long id);

    void addStep(QuestStep step);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    QuestStep getRoot();

    void setRoot(QuestStep root);
}
