package com.IsmailUsman_i180516_i180634;

public class Message {
    private String content;
    private String sender;
    private String receiver;
    private String senderPhoto;
    public String photo;
    public boolean isPhoto;
    private boolean liked;
    private String time;
    private boolean read;

    public Message()
    {
        this.content = "";
        this.time = "";
        read = false;
        liked = false;
        isPhoto = false;
        this.sender = "";
        this.receiver ="";

    }
    public Message(String content, String time, boolean read) {
        this.content = content;
        this.time = time;
        this.read = read;
        liked = false;
        isPhoto = false;
        this.sender = "";
        this.receiver ="";
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String  getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
    public String getSenderPhoto() {
        return senderPhoto;
    }

    public void setSenderPhoto(String senderPhoto) {
        this.senderPhoto = senderPhoto;
    }

    public boolean getIsPhoto() {
        return isPhoto;
    }

    public void setIsPhoto(boolean photo) {
        isPhoto = photo;
    }
}
