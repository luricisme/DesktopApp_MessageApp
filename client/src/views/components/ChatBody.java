package views.components;

import app.MessageType;
import java.awt.Adjustable;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;
import models.ReceiveMessageModel;
import models.SendMessageModel;
import net.miginfocom.swing.MigLayout;
import views.emoji.Emoji;
import views.swing.ScrollBar;

public class ChatBody extends javax.swing.JPanel {

    public ChatBody() {
        initComponents();
        init();
//        addItemRight("Ok\nWhat is his name ?");
    }

    private void init() {
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }

    public void addItemLeft(ReceiveMessageModel data) {
        if (data.getMessageType() == MessageType.TEXT) {
            ChatLeft item = new ChatLeft();
            item.setText(data.getText());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        } else if (data.getMessageType() == MessageType.EMOJI) {
            ChatLeft item = new ChatLeft();
            item.setEmoji(Emoji.getInstance().getEmoji(Integer.valueOf(data.getText())).getIcon());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        } else if(data.getMessageType() == MessageType.IMAGE){
            ChatLeft item = new ChatLeft();
            item.setText(data.getText());
            item.setImage(data.getDataImage());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        }
        repaint();
        revalidate();
        scrollToBottom();
    }

    public void addItemLeft(String text, String user, String[] images) {
        ChatLeftWithProfile item = new ChatLeftWithProfile();
        item.setText(text);
        item.setImage(images);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        body.repaint();
        body.revalidate();
    }

    public void addItemRight(SendMessageModel data) {
        if (data.getMessageType() == MessageType.TEXT) {
            ChatRight item = new ChatRight();
            item.setText(data.getText());
            item.setTime();
            body.add(item, "wrap, al right, w 100::80%");
        } else if (data.getMessageType() == MessageType.EMOJI) {
            ChatRight item = new ChatRight();
            item.setEmoji(Emoji.getInstance().getEmoji(Integer.valueOf(data.getText())).getIcon());
            body.add(item, "wrap, al right, w 100::80%");
            item.setTime();
        } else if (data.getMessageType() == MessageType.IMAGE) {
            ChatRight item = new ChatRight();
            item.setText("");
            item.setImage(data.getFile());
            item.setTime();
            body.add(item, "wrap, al right, w 100::80%");
        }
//        } else if (data.getMessageType() == MessageType.FILE) {
//            ChatRight item = new ChatRight();
//            item.setText("");
//            item.setImage(data.getFile());
//            item.setTime();
//            body.add(item, "wrap, al right, w 100::80%");
//        }
        repaint();
        revalidate();
        scrollToBottom();
    }

    public void addItemFile(String text, String user, String fileName, String fileSize) {
        ChatLeftWithProfile item = new ChatLeftWithProfile();
        item.setText(text);
        item.setFile(fileName, fileSize);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        body.repaint();
        body.revalidate();
    }

    public void addItemFileRight(String text, String fileName, String fileSize) {
        ChatRight item = new ChatRight();
        item.setText(text);
        item.setFile(fileName, fileSize);
        body.add(item, "wrap, al right, w 100::80%");
        body.repaint();
        body.revalidate();
    }

    public void addDate(String date) {
        ChatDate item = new ChatDate();
        item.setDate(date);
        body.add(item, "wrap, al center");
        body.repaint();
        body.revalidate();
    }

    public void clearChat() {
        body.removeAll();
        repaint();
        revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();

        sp.setBackground(new java.awt.Color(255, 255, 255));
        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        body.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 667, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 564, Short.MAX_VALUE)
        );

        sp.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
    }// </editor-fold>//GEN-END:initComponents
    private void scrollToBottom() {
        JScrollBar verticalBar = sp.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
