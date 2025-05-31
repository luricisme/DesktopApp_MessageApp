package events;

import models.LoginModel;
import models.RegisterModel;

public interface EventLogin {
    public void login(LoginModel data);
    public void register(RegisterModel data, EventMessage message);
    public void goRegister();
    public void goLogin();
}
