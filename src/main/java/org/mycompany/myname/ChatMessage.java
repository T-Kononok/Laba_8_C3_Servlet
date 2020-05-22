package org.mycompany.myname;

public class ChatMessage {
    private String message;
    private ChatUser author;
    private long timestamp;
    ChatMessage(String message, ChatUser author, long timestamp) {
        super();
        this.message = message;
        this.author = author;
        this.timestamp = timestamp;
    }
    String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    ChatUser getAuthor() {
        return author;
    }
    public void setAuthor(ChatUser author) {
        this.author = author;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

