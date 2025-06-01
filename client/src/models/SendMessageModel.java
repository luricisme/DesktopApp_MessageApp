package models;

import org.json.JSONException;
import org.json.JSONObject;

public class SendMessageModel {

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    int fromUserID;
    int toUserID;
    String text;
    
    public JSONObject toJSONObject(){
        try{
            JSONObject json = new JSONObject();
            json.put("fromUserID", fromUserID);
            json.put("toUserID", toUserID);
            json.put("text", text);
            return json;
        } catch(JSONException e){
            return null;
        }
    }

    public SendMessageModel() {
    }
    
    public SendMessageModel(int fromUserID, int toUserID, String text) {
        this.fromUserID = fromUserID;
        this.toUserID = toUserID;
        this.text = text;
    }
}
