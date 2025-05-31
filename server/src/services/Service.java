package services;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JTextArea;
import models.LoginModel;
import models.MessageModel;
import models.RegisterModel;
import models.UserAccountModel;

public class Service {

    private static Service instance;
    private SocketIOServer server;
    private UserService userService;
    private JTextArea textArea;

    private final int PORT_NUMBER = 9999;

    public static Service getInstance(JTextArea textArea) {
        if (instance == null) {
            instance = new Service(textArea);
        }
        return instance;
    }

    private Service(JTextArea textArea) {
        this.textArea = textArea;
        this.userService = new UserService();
    }

    public void startServer() {
        Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient sioc) {
                textArea.append("One client connected\n");
            }
        });

        server.addEventListener("register", RegisterModel.class, new DataListener<RegisterModel>() {
            @Override
            public void onData(SocketIOClient sioc, RegisterModel t, AckRequest ar) throws Exception {
                System.out.println("Data received: " + t.getUserName() + " Pass: " + t.getPassword());
                MessageModel message = userService.register(t);
                ar.sendAckData(message.isAction(), message.getMessage(), message.getData());
                if (message.isAction()) {
                    textArea.append("User Register: " + t.getUserName() + " Pass: " + t.getPassword() + "\n");
                    server.getBroadcastOperations().sendEvent("list_user", (UserAccountModel) message.getData());
                }
            }

        });

        server.addEventListener("login", LoginModel.class, new DataListener<LoginModel>() {
            @Override
            public void onData(SocketIOClient sioc, LoginModel t, AckRequest ar) throws Exception {
                UserAccountModel login = userService.login(t);
                
                if(login != null){
                    ar.sendAckData(true, login);
                } else{
                    ar.sendAckData(false);
                }
            }
        });

        server.addEventListener("list_user", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer userID, AckRequest ar) throws Exception {
                try {
                    List<UserAccountModel> list = userService.getUser(userID);
                    sioc.sendEvent("list_user", list.toArray());
                } catch (SQLException e) {
                    System.err.println(e);
                }
            }
        });
        server.start();
        textArea.append("Server has start on port :" + PORT_NUMBER + "\n");
    }
}
