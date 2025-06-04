package models;

import com.corundumstudio.socketio.SocketIOClient;

public class ClientModel {

    SocketIOClient client;
    UserAccountModel user;

    public SocketIOClient getClient() {
        return client;
    }

    public void setClient(SocketIOClient client) {
        this.client = client;
    }

    public UserAccountModel getUser() {
        return user;
    }

    public void setUser(UserAccountModel user) {
        this.user = user;
    }

    public ClientModel(SocketIOClient client, UserAccountModel user) {
        this.client = client;
        this.user = user;
    }

    public ClientModel() {
    }
}
