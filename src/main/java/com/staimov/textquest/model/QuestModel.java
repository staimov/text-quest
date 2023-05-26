package com.staimov.textquest.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class QuestModel {
    private static final Logger logger = LoggerFactory.getLogger(QuestModel.class);

    private QuestStep root;
    private String name;
    private String description;
    private final ConcurrentMap<Long, QuestStep> steps = new ConcurrentHashMap<>();

    public QuestModel() {
        logger.debug("Inside QuestModel() constructor");
    }

    public QuestModel(String name) {
        logger.debug("Inside QuestModel(String name) constructor");

        this.name = name;
    }

    public QuestStep getStep(long id) {
        return steps.get(id);
    }

    public boolean containsStep(long id) {
        return steps.containsKey(id);
    }

    public void clearSteps() {
        root = null;
        steps.clear();
    }

    public void addStep(QuestStep step) {
        steps.put(step.getId(), step);
    }

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

    public QuestStep getRoot() {
        return root;
    }

    public void setRoot(QuestStep root) {
        this.root = root;
    }
}
