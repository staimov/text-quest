package com.staimov.textquest.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@SessionScope
public class SessionData {
    private static final Logger logger = LoggerFactory.getLogger(SessionData.class);

    private final AtomicInteger startCount = new AtomicInteger(0);
    private final AtomicInteger completeCount = new AtomicInteger(0);
    private String playerName = "Homer";

    public SessionData() {
        logger.debug("Inside SessionData() constructor!");
    }

    public int getStartCount() {
        return startCount.get();
    }

    public int getCompleteCount() {
        return completeCount.get();
    }

    public void incrementStartCount() {
        startCount.incrementAndGet();
    }

    public void incrementCompleteCount() {
        completeCount.incrementAndGet();
    }

    public void resetStartCount() {
        startCount.set(0);
    }

    public void resetCompleteCount() {
        completeCount.set(0);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
