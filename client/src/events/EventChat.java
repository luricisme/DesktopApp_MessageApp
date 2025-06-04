package events;

import java.util.List;
import models.ReceiveMessageModel;
import models.SendMessageModel;
import models.ViewMessageModel;

public interface EventChat {
    public void sendMessage(SendMessageModel data);
    public void receiveMessage(ReceiveMessageModel data);
    public void showHistoryMessage(List<ViewMessageModel> data);
}
