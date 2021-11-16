package com.IsmailUsman_i180516_i180634;

import java.io.Serializable;
import java.util.HashMap;

public class Chat implements Serializable {
    private String receiver;
    private String sender;
    private String lastTime;
    private String lastMessage;
    private HashMap<String,Message> messages;

    public Chat()
    {
        this.receiver = new String();
        this.sender = new String();
        lastMessage = new String();
        this.messages = new HashMap<>();
    }
    public Chat(String receiver, String sender) {

        this.receiver = receiver;
        this.sender = sender;
        lastMessage = new String();
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }
    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
    public HashMap<String, Message> getMessages() {
        return messages;
    }

    public void setMessages(HashMap<String, Message> messages) {
        this.messages = messages;
    }


}
