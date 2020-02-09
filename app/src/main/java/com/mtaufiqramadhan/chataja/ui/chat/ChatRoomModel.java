package com.mtaufiqramadhan.chataja.ui.chat;

import java.util.Date;

public class ChatRoomModel {

    private String avatar, name, sender, receiver, message;
    private Date timestamp;

    public ChatRoomModel() {
    }

    public ChatRoomModel(String avatar, String name, String sender, String receiver, String message, Date timestamp) {
        this.avatar = avatar;
        this.name = name;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
