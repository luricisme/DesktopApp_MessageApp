package events;

import models.RegisterModel;

public interface EventLogin {
    public void login();
    public void register(RegisterModel data);
    public void goRegister();
    public void goLogin();
}
