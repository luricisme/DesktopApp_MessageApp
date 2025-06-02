package models;

import app.MessageType;

public class ReceiveMessageModel {

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ReceiveMessageModel(int messageType, int fromUserID, String text) {
        this.messageType = messageType;
        this.fromUserID = fromUserID;
        this.text = text;
    }

    public ReceiveMessageModel() {
    }

    private int messageType;
    private int fromUserID;
    private String text;
}
