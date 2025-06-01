package events;

import models.ReceiveMessageModel;
import models.SendMessageModel;

public interface EventChat {
    public void sendMessage(SendMessageModel data);
    public void receiveMessage(ReceiveMessageModel data);
}
