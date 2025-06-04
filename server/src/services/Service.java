package services;

import app.MessageType;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextArea;
import models.ClientModel;
import models.FileModel;
import models.LoginModel;
import models.MessageModel;
import models.PackageSenderModel;
import models.ReceiveImageModel;
import models.ReceiveMessageModel;
import models.RegisterModel;
import models.RequestFileModel;
import models.SendMessageModel;
import models.UserAccountModel;
import models.ViewMessageModel;
import models.ViewMessageRequest;

public class Service {

    private static Service instance;
    private SocketIOServer server;
    private UserService userService;
    private FileService fileService;
    private MessageService messageService;
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
        this.fileService = new FileService();
        this.messageService = new MessageService();
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

                if (login != null) {
                    ar.sendAckData(true, login);
                    addClient(sioc, login);
                    userConnect(login.getUserID());
                } else {
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

        server.addEventListener("send_to_user", SendMessageModel.class, new DataListener<SendMessageModel>() {
            @Override
            public void onData(SocketIOClient sioc, SendMessageModel t, AckRequest ar) throws Exception {
                sendToClient(t, ar);
            }
        });

        server.addEventListener("send_file", PackageSenderModel.class, new DataListener<PackageSenderModel>() {
            @Override
            public void onData(SocketIOClient sioc, PackageSenderModel t, AckRequest ar) throws Exception {
                try {
                    fileService.receiveFile(t);
                    if (t.isFinish()) {
                        ar.sendAckData(true);
                        ReceiveImageModel dataImage = new ReceiveImageModel();
                        dataImage.setFileID(t.getFileID());
                        SendMessageModel message = fileService.closeFile(dataImage);
                        // Send to client 'message'
                        sendTempFileToClient(message, dataImage);
                    } else {
                        ar.sendAckData(true);
                    }
                } catch (IOException | SQLException e) {
                    ar.sendAckData(true);
                    e.printStackTrace();
                }
            }
        });

        server.addEventListener("get_file", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient sioc, Integer t, AckRequest ar) throws Exception {
                FileModel file = fileService.initFile(t);
                long fileSize = fileService.getFileSize(t);
                ar.sendAckData(file.getFileExtension(), fileSize);
            }
        });

        server.addEventListener("request_file", RequestFileModel.class, new DataListener<RequestFileModel>() {
            @Override
            public void onData(SocketIOClient sioc, RequestFileModel t, AckRequest ar) throws Exception {
                byte[] data = fileService.getFileData(t.getCurrentLength(), t.getFileID());
                if (data != null) {
                    ar.sendAckData(data);
                } else {
                    ar.sendAckData();
                }
            }
        });

        server.addEventListener("message_history", ViewMessageRequest.class, new DataListener<ViewMessageRequest>() {
            @Override
            public void onData(SocketIOClient client, ViewMessageRequest data, AckRequest ackRequest) throws Exception {
                // Giả sử messageService có phương thức lấy tin nhắn giữa 2 user
                System.out.println("SERVER RECEIVE: " + data.getFromUserID() + " and " + data.getToUserID());
                List<ViewMessageModel> history = messageService.getMessages(data.getFromUserID(), data.getToUserID());
                System.out.println("SERVER RETURN HISTORY MESSAGE TO CLIENT" + history.size());
                // Trả dữ liệu cho client
                ackRequest.sendAckData(history.toArray());
            }
        }); 

        server.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient sioc) {
                int userID = removeClient(sioc);
                if (userID != 0) {
                    // Removed
                    userDisconnect(userID);
                }
            }

        });

        server.start();
        textArea.append("Server has start on port :" + PORT_NUMBER + "\n");
    }

    private void userConnect(int userID) {
        server.getBroadcastOperations().sendEvent("user_status", userID, true);
    }

    private void userDisconnect(int userID) {
        server.getBroadcastOperations().sendEvent("user_status", userID, false);
    }

    private void addClient(SocketIOClient client, UserAccountModel user) {
        listClient.add(new ClientModel(client, user));
    }

    public void sendToClient(SendMessageModel data, AckRequest ar) {
        if (data.getMessageType() == MessageType.IMAGE.getValue() || data.getMessageType() == MessageType.FILE.getValue()) {
            try {
                FileModel file = fileService.addFileReceiver(data.getText());
                fileService.initFile(file, data);

                boolean saved = messageService.insertMessage(
                        data.getFromUserID(),
                        data.getToUserID(),
                        data.getMessageType(),
                        data.getText(),
                        file.getFileID()
                );
                if (saved) {
                    ar.sendAckData(file.getFileID());
                } else {
                    System.out.println("Failed to save file message");
                    ar.sendAckData(false, "Failed to save file message");
                }
//                ar.sendAckData(file.getFileID());
            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            boolean saved = messageService.insertMessage(
                    data.getFromUserID(),
                    data.getToUserID(),
                    data.getMessageType(),
                    data.getText(),
                    null
            );
            if (saved) {
                for (ClientModel c : listClient) {
                    if (c.getUser().getUserID() == data.getToUserID()) {
                        c.getClient().sendEvent("receive_ms", new ReceiveMessageModel(
                                data.getMessageType(),
                                data.getFromUserID(),
                                data.getText(),
                                null
                        ));
                        break;
                    }
                }
                ar.sendAckData(true);
            } else {
                System.out.println("Failed to save file message");
                ar.sendAckData(false, "Failed to save message");
            }
//            for (ClientModel c : listClient) {
//                if (c.getUser().getUserID() == data.getToUserID()) {
//                    c.getClient().sendEvent("receive_ms", new ReceiveMessageModel(data.getMessageType(), data.getFromUserID(), data.getText(), null));
//                    break;
//                }
//            }
        }
    }

    private void sendTempFileToClient(SendMessageModel data, ReceiveImageModel dataImage) {
        for (ClientModel c : listClient) {
            if (c.getUser().getUserID() == data.getToUserID()) {
                c.getClient().sendEvent("receive_ms", new ReceiveMessageModel(data.getMessageType(), data.getFromUserID(), data.getText(), dataImage));
                break;
            }
        }
    }

    public int removeClient(SocketIOClient client) {
        for (ClientModel d : listClient) {
            if (d.getClient() == client) {
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
