package views.components;

import app.MessageType;
import events.PublicEvent;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import models.SendMessageModel;
import models.UserAccountModel;
import net.miginfocom.swing.MigLayout;
import services.Service;
import views.swing.JIMSendTextPane;
import views.swing.ScrollBar;

public class ChatBottom extends javax.swing.JPanel {

    private UserAccountModel user;

    public UserAccountModel getUser() {
        return user;
    }

    public void setUser(UserAccountModel user) {
        this.user = user;
        morePanel.setUser(user);
    }

    public ChatBottom() {
        initComponents();
        init();
    }

    public void init() {
        mig = new MigLayout("fillx, filly", "2[fill]0[]2", "2[fill]2[]0");
        setLayout(mig);
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        JIMSendTextPane txt = new JIMSendTextPane();
        txt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                refresh();
                if (ke.getKeyChar() == 10 && ke.isControlDown()) {
                    // User press control + enter
                    eventSend(txt);
                }
            }
        });
        txt.setHintText("Write Message Here ...");
        scroll.setViewportView(txt);
        ScrollBar sb = new ScrollBar();
        sb.setPreferredSize(new Dimension(2, 10));
        scroll.setVerticalScrollBar(sb);
        add(scroll, "w 100%");
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("filly", "0[]3[]0", "0[bottom]0"));
        panel.setPreferredSize(new Dimension(30, 28));
        JButton cmd = new JButton();
        cmd.setBorder(null);
        panel.setBackground(Color.WHITE);
        cmd.setContentAreaFilled(false);
        cmd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmd.setIcon(new ImageIcon(getClass().getResource("/icons/send.png")));
        cmd.addActionListener((e) -> {
            eventSend(txt);
        });

        JButton cmdMore = new JButton();
        cmdMore.setBorder(null);
        panel.setBackground(Color.WHITE);
        cmdMore.setContentAreaFilled(false);
        cmdMore.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdMore.setIcon(new ImageIcon(getClass().getResource("/icons/more_disable.png")));
        cmdMore.addActionListener((e) -> {
            if (morePanel.isVisible()) {
                cmdMore.setIcon(new ImageIcon(getClass().getResource("/icons/more_disable.png")));
                morePanel.setVisible(false);
                mig.setComponentConstraints(morePanel, "dock south, h 0!");
                revalidate();
            } else {
                cmdMore.setIcon(new ImageIcon(getClass().getResource("/icons/more.png")));
                morePanel.setVisible(true);
                mig.setComponentConstraints(morePanel, "dock south, h 170!");
                revalidate();
            }
        });
        panel.add(cmdMore);
        panel.add(cmd);
        add(panel, "wrap");
        morePanel = new MorePanel();
        add(morePanel, "dock south, h 0!");
    }

    private void eventSend(JIMSendTextPane txt) {
        String text = txt.getText().trim();
        if (!text.equals("")) {
            // Add chat item
            SendMessageModel message = new SendMessageModel(MessageType.TEXT, Service.getInstance().getUser().getUserID(), user.getUserID(), text);
            send(message);
            PublicEvent.getInstance().getEventChat().sendMessage(message);
            txt.setText("");
            txt.grabFocus();
            refresh();
        } else {
            txt.grabFocus();
        }
    }

    private void send(SendMessageModel data) {
        Service.getInstance().getClient().emit("send_to_user", data.toJSONObject());
    }

    private void refresh() {
        revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public ChatBottom(UserAccountModel user) {
        this.user = user;
    }

    private MigLayout mig;
    private MorePanel morePanel;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
