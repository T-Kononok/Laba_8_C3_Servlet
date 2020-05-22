package org.mycompany.myname;

public class ChatUser {
    private String name;
    private long lastInteractionTime;
    private String sessionId;
    ChatUser(String name, long lastInteractionTime, String sessionId) {
        super();
        this.name = name;
        this.lastInteractionTime = lastInteractionTime;
        this.sessionId = sessionId;
    }
    String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    long getLastInteractionTime() {
        return lastInteractionTime;
    }
    void setLastInteractionTime(long lastInteractionTime) {
        this.lastInteractionTime = lastInteractionTime;
    }
    String getSessionId() {
        return sessionId;
    }
    void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

