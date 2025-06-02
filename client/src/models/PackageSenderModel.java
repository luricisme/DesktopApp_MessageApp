package models;

import org.json.JSONException;
import org.json.JSONObject;

public class PackageSenderModel {

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public PackageSenderModel() {
    }

    public PackageSenderModel(int fileID, byte[] data, boolean finish) {
        this.fileID = fileID;
        this.data = data;
        this.finish = finish;
    }
    
    int fileID;
    byte[] data;
    boolean finish;
    
    public JSONObject toJSONObject(){
        try{
            JSONObject json = new JSONObject();
            json.put("fileID", fileID);
            json.put("data", data);
            json.put("finish", finish);
            return json;
        } catch(JSONException e){
            return null;
        }
    }
}
