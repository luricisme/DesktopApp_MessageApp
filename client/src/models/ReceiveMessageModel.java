package models;

import app.MessageType;
import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveMessageModel {

    private MessageType messageType;
    private int fromUserID;
    private String text;
    private ReceiveImageModel dataImage;

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
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

    public ReceiveMessageModel() {
    }

    public ReceiveMessageModel(MessageType messageType, int fromUserID, String text, ReceiveImageModel dataImage) {
        this.messageType = messageType;
        this.fromUserID = fromUserID;
        this.text = text;
        this.dataImage = dataImage;
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("messageType", messageType.getValue());
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            if (dataImage != null) {
                json.put("dataImage", dataImage.toJSONObject());
            }
            return json;
        } catch (JSONException e) {
            return null;
        }
    }

    public ReceiveMessageModel(Object json) {
        JSONObject obj = (JSONObject) json;
        try {
            messageType = MessageType.toMessageType(obj.getInt("messageType"));
            fromUserID = obj.getInt("fromUserID");
            text = obj.getString("text");
            if (!obj.isNull("dataImage")) {
                dataImage = new ReceiveImageModel(obj.get("dataImage"));
            }
        } catch (JSONException e) {
            System.err.println(e);
        }
    }
}
