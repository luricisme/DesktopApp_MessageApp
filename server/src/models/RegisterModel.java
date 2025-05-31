package models;

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
    
    public RegisterModel(){
        
    }
    
    public RegisterModel(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
}
