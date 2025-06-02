package models;

import app.MessageType;

public class ReceiveMessageModel {

    private int messageType;
    private int fromUserID;
    private String text;
    ReceiveImageModel dataImage;

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

    public ReceiveImageModel getDataImage() {
        return dataImage;
    }

    public void setDataImage(ReceiveImageModel dataImage) {
        this.dataImage = dataImage;
    }

    public ReceiveMessageModel(int messageType, int fromUserID, String text, ReceiveImageModel dataImage) {
        this.messageType = messageType;
        this.fromUserID = fromUserID;
        this.text = text;
        this.dataImage = dataImage;
    }

    public ReceiveMessageModel() {
    }
}
