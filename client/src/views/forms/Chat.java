package views.forms;

import app.MessageType;
import events.EventChat;
import events.PublicEvent;
import java.util.List;
import models.ReceiveMessageModel;
import models.SendMessageModel;
import models.UserAccountModel;
import models.ViewMessageModel;
import net.miginfocom.swing.MigLayout;
import services.Service;
import views.components.ChatBody;
import views.components.ChatBottom;
import views.components.ChatTitle;

public class Chat extends javax.swing.JPanel {

    private ChatTitle chatTitle;
    private ChatBody chatBody;
    private ChatBottom chatBottom;

    public Chat() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx, filly", "0[fill]0", "0[]0[100%, fill]0[shrink 0]0"));
        chatTitle = new ChatTitle();
        chatBody = new ChatBody();
        chatBottom = new ChatBottom();
        PublicEvent.getInstance().addEventChat(new EventChat() {
            @Override
            public void sendMessage(SendMessageModel data) {
                chatBody.addItemRight(data);
            }

            @Override
            public void receiveMessage(ReceiveMessageModel data) {
                if (chatTitle.getUser().getUserID() == data.getFromUserID()) {
                    chatBody.addItemLeft(data);
                }
            }

            @Override
            public void showHistoryMessage(List<ViewMessageModel> data) {
                int currentUserId = Service.getInstance().getUser().getUserID();
                chatBody.clearChat();

                for (ViewMessageModel msg : data) {
                    if (msg.getFromUserId() == currentUserId) {
                        SendMessageModel sm = new SendMessageModel(MessageType.TEXT, msg.getFromUserId(), msg.getToUserId(), msg.getContent());
                        chatBody.addItemRight(sm);
                    } else {
                        ReceiveMessageModel rm = new ReceiveMessageModel(MessageType.TEXT, msg.getFromUserId(), msg.getContent(), null);
                        chatBody.addItemLeft(rm);
                    }
                }
            }
        });
        add(chatTitle, "wrap");
        add(chatBody, "grow, push, wrap");
        add(chatBottom, "h ::50%");
//        chatBody.setVisible(false);
    }

    public void setUser(UserAccountModel user) {
        chatTitle.setUserName(user);
        chatBottom.setUser(user);
        chatBody.clearChat();
    }

    public void updateUser(UserAccountModel user) {
        chatTitle.updateUser(user);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 637, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 577, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
