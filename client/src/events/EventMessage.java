package events;

import models.MessageModel;

public interface EventMessage {
    public void callMessage(MessageModel message);
}
