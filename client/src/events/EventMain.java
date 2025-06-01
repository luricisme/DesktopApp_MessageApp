package events;

import models.UserAccountModel;

public interface EventMain {

    public void showLoading(boolean show);

    public void initChat();

    public void selectUser(UserAccountModel user);

    public void updateUser(UserAccountModel user);
}
