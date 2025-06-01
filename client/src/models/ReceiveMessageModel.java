package models;

import org.json.JSONException;
import org.json.JSONObject;

public class ReceiveMessageModel {
    int fromUserID;

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
    String text;
    
    public JSONObject toJSONObject(){
        try{
            JSONObject json = new JSONObject();
            json.put("fromUserID", fromUserID);
            json.put("text", text);
            return json;
        } catch(JSONException e){
            return null;
        }
    }

    public ReceiveMessageModel() {
    }
    
    public ReceiveMessageModel(Object json){
        JSONObject obj = (JSONObject) json;
        try{
            fromUserID = obj.getInt("fromUserID");
            text = obj.getString("text");
        } catch(JSONException e){
            System.err.println(e);
        }
    }
    
    public ReceiveMessageModel(int fromUserID, String text) {
        this.fromUserID = fromUserID;
        this.text = text;
    }
}
