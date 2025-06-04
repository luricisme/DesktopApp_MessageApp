package services;

import events.PublicEvent;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import models.FileReceiverModel;
import models.FileSenderModel;
import models.ReceiveMessageModel;
import models.SendMessageModel;
import models.UserAccountModel;
import events.EventFileReceiver;
import models.ViewMessageModel;

public class Service {

    private static Service instance;
    private Socket client;
    private final int PORT_NUMBER = 9999;
    private final String IP = "localhost";
    private UserAccountModel user;
    private List<FileSenderModel> fileSender;
    private List<FileReceiverModel> fileReceiver;

    public static Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    private Service() {
        fileSender = new ArrayList<>();
        fileReceiver = new ArrayList<>();
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
                    int userID = (Integer) os[0];
                    boolean status = (Boolean) os[1];
                    if (status) {
                        // Connect
                        PublicEvent.getInstance().getEventMenuLeft().userConnect(userID);
                    } else {
                        // Disconnect
                        PublicEvent.getInstance().getEventMenuLeft().userDisconnect(userID);
                    }
                }
            });

            client.on("receive_ms", new Emitter.Listener() {
                @Override
                public void call(Object... os) {
                    ReceiveMessageModel message = new ReceiveMessageModel(os[0]);
                    System.out.println("PREPARE SHOW MESSAGE");
                    PublicEvent.getInstance().getEventChat().receiveMessage(message);
                }
            });

//            client.on("message_history", new Emitter.Listener() {
//                @Override
//                public void call(Object... os) {
//                    List<ViewMessageModel> messages = new ArrayList<>();
//                    for (Object o : os) {
//                        messages.add(new ViewMessageModel(o));
//                    }
//                    // Gửi cho UI hiển thị
//                    System.out.println("DATA: " + messages);
//                    System.out.println("PREPARE SHOW HISTORY CHAT");
//                    PublicEvent.getInstance().getEventChat().showHistoryMessage(messages);
//                }
//            });

            client.open();
        } catch (URISyntaxException e) {
            error(e);
        }
    }

    public FileSenderModel addFile(File file, SendMessageModel message) throws IOException {
        FileSenderModel data = new FileSenderModel(file, client, message);
        message.setFile(data);
        fileSender.add(data);
        // For send file one by one
        if (fileSender.size() == 1) {
            data.initSend();
        }
        return data;
    }

    public void fileSendFinish(FileSenderModel data) throws IOException {
        fileSender.remove(data);
        if (!fileSender.isEmpty()) {
            // Start send new file when old file sending finish
            fileSender.get(0).initSend();
        }
    }

    public void fileReceiveFinish(FileReceiverModel data) throws IOException {
        fileReceiver.remove(data);
        if (!fileReceiver.isEmpty()) {
            fileSender.get(0).initSend();
        }
    }

    public void addFileReceiver(int fileID, EventFileReceiver event) throws IOException {
        FileReceiverModel data = new FileReceiverModel(fileID, client, event);
        fileReceiver.add(data);
        if (fileReceiver.size() == 1) {
            data.initReceive();
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
