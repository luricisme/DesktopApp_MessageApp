package services;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import models.ClientModel;
import models.LoginModel;
import models.MessageModel;
import models.RegisterModel;
import models.UserAccountModel;

public class Service {

    private static Service instance;
    private SocketIOServer server;
    private UserService userService;
    private List<ClientModel> listClient;
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
        listClient = new ArrayList<>();
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
                    addClient(sioc, (UserAccountModel) message.getData());
                }
            }

        });

        server.addEventListener("login", LoginModel.class, new DataListener<LoginModel>() {
            @Override
            public void onData(SocketIOClient sioc, LoginModel t, AckRequest ar) throws Exception {
                UserAccountModel login = userService.login(t);
                
                if(login != null){
                    ar.sendAckData(true, login);
                    addClient(sioc, login);
                    userConnect(login.getUserID());
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
        
        server.addDisconnectListener(new DisconnectListener(){
            @Override
            public void onDisconnect(SocketIOClient sioc) {
                int userID = removeClient(sioc);
                if(userID != 0){
                    // Removed
                    userDisconnect(userID);
                }
            }
            
        });
        
        server.start();
        textArea.append("Server has start on port :" + PORT_NUMBER + "\n");
    }
    
    private void userConnect(int userID){
        server.getBroadcastOperations().sendEvent("user_status", userID, true);
    }
    
    private void userDisconnect(int userID){
        server.getBroadcastOperations().sendEvent("user_status", userID, false);
    }
    
    private void addClient(SocketIOClient client, UserAccountModel user){
        listClient.add(new ClientModel(client, user));
    }
    
    public int removeClient(SocketIOClient client){
        for(ClientModel d: listClient){
            if(d.getClient() == client){
                listClient.remove(d);
                return d.getUser().getUserID();
            }
        }
        return 0;
    }
    
    public List<ClientModel> getListClient() {
        return listClient;
    }
}
