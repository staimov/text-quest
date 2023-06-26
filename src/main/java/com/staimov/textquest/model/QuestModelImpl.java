package com.staimov.textquest.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class QuestModelImpl implements QuestModel {
    private static final Logger logger = LoggerFactory.getLogger(QuestModelImpl.class);

    private QuestStep root;
    private String name;
    private String description;
    private final ConcurrentMap<Long, QuestStep> steps = new ConcurrentHashMap<>();

    public QuestModelImpl() {
        logger.debug("Inside QuestModelImpl() constructor");
    }

    public QuestModelImpl(String name) {
        logger.debug("Inside QuestModel(String name) constructor");

        this.name = name;
    }

    @Override
    public QuestStep getStep(long id) {
        return steps.get(id);
    }

    @Override
    public boolean containsStep(long id) {
        return steps.containsKey(id);
    }

    @Override
    public void addStep(QuestStep step) {
        steps.put(step.getId(), step);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public QuestStep getRoot() {
        return root;
    }

    @Override
    public void setRoot(QuestStep root) {
        this.root = root;
    }
}
