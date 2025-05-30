package views.forms;

import events.EventChat;
import events.PublicEvent;
import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;
import views.components.ChatBody;
import views.components.ChatBottom;
import views.components.ChatTitle;

public class Chat extends javax.swing.JPanel {

    public Chat() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx, filly", "0[fill]0", "0[]0[100%, bottom]0[shrink 0]0"));
        ChatTitle chatTitle = new ChatTitle();
        ChatBody chatBody = new ChatBody();
        ChatBottom chatBottom = new ChatBottom();
        PublicEvent.getInstance().addEventChat(new EventChat() {
            @Override
            public void sendMessage(String text) {
                chatBody.addItemRight(text);
            }
        });
        add(chatTitle, "wrap");
        add(chatBody, "grow, push, wrap");
        add(chatBottom, "h ::50%");
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
