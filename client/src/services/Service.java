package services;

import events.PublicEvent;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import models.ReceiveMessageModel;
import models.UserAccountModel;

public class Service {

    private static Service instance;
    private Socket client;
    private final int PORT_NUMBER = 9999;
    private final String IP = "localhost";
    private UserAccountModel user;

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    private Service() {
    }

    public void startServer() {
        try {
            client = IO.socket("http://" + IP + ":" + PORT_NUMBER);
            client.on("list_user", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    // List user 
                    List<UserAccountModel> users = new ArrayList<>();
                    for (Object o : os) {
                        UserAccountModel u = new UserAccountModel(o);
                        if (u.getUserID() != user.getUserID()) {
                            users.add(u);
                        }
                    }
                    PublicEvent.getInstance().getEventMenuLeft().newUser(users);
                }
            });

            client.on("user_status", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    int userID = (Integer)os[0];
                    boolean status = (Boolean) os[1];
                    if(status){
                        // Connect
                        PublicEvent.getInstance().getEventMenuLeft().userConnect(userID);
                    } else{
                        // Disconnect
                        PublicEvent.getInstance().getEventMenuLeft().userDisconnect(userID);
                    }
                }
            });
            
            client.on("receive_ms", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    ReceiveMessageModel message = new ReceiveMessageModel(os[0]);
                    PublicEvent.getInstance().getEventChat().receiveMessage(message);
                }
            });

            client.open();
        } catch (URISyntaxException e) {
            error(e);
        }
    }

    public Socket getClient() {
        return client;
    }

    public UserAccountModel getUser() {
        return user;
    }

    public void setUser(UserAccountModel user) {
        this.user = user;
    }

    private void error(Exception e) {
        System.err.println(e);
    }
}
