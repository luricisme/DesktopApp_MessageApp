package models;

public class ViewMessageRequest {

    private int fromUserID;
    private int toUserID;

    public ViewMessageRequest() {
    }

    public ViewMessageRequest(int fromUserID, int toUserID) {
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
    }

    public int getFromUserID() {
        return fromUserID;
    }

    public void setFromUserID(int fromUserID) {
        this.fromUserID = fromUserID;
    }

    public int getToUserID() {
        return toUserID;
    }

    public void setToUserID(int toUserID) {
        this.toUserID = toUserID;
    }
}
