package services;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import javax.swing.JTextArea;
import models.RegisterModel;

public class Service {
    private static Service instance;
    private SocketIOServer server;
    private JTextArea textArea;
    
    private final int PORT_NUMBER = 9999;
    
    public static Service getInstance(JTextArea textArea){
        if(instance == null){
            instance = new Service(textArea);
        }
        return instance;
    }
    
    private Service(JTextArea textArea){
        this.textArea = textArea;
    }
    
    public void startServer(){
        Configuration config = new Configuration();
        config.setPort(PORT_NUMBER);
        server = new SocketIOServer(config);
        server.addConnectListener(new ConnectListener(){
            @Override
            public void onConnect(SocketIOClient sioc) {
                textArea.append("One client connected\n");
            }
        });
        
        server.addEventListener("register", RegisterModel.class, new DataListener<RegisterModel>(){
            @Override
            public void onData(SocketIOClient sioc, RegisterModel t, AckRequest ar) throws Exception {
                System.out.println("Data received: " + t.getUserName() + " Pass: " + t.getPassword());
                textArea.append("User Register: " + t.getUserName() + " Pass: " + t.getPassword() + "\n");
            }
            
        });
        
        server.start();
        textArea.append("Server has start on port :" + PORT_NUMBER + "\n");
    }
}
