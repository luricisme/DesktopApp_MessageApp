package events;

import java.util.List;
import models.UserAccountModel;

public interface EventMenuLeft {
    public void newUser(List<UserAccountModel> users);
}
