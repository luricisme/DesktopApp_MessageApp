package models;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterModel {

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    private String userName;
    private String password;
    
    public RegisterModel(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public JSONObject toJsonObject() {
        try {
            JSONObject json = new JSONObject();
            json.put("userName", userName);
            json.put("password", password);
            return json;
        } catch (JSONException ex) {
            return null;
        }
    }
}
