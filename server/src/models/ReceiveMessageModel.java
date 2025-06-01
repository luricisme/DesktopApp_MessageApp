package models;

public class ReceiveMessageModel {

    int fromUserID;
    String text;

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

    public ReceiveMessageModel() {
    }

    public ReceiveMessageModel(int fromUserID, String text) {
        this.fromUserID = fromUserID;
        this.text = text;
    }
}
