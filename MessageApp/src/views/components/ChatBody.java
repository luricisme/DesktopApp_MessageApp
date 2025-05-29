package views.components;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import net.miginfocom.swing.MigLayout;
import views.swing.ScrollBar;

public class ChatBody extends javax.swing.JPanel {

    public ChatBody() {
        initComponents();
        init();
        addItemLeft("Hello anh ban chao anh ban nha aaaaaaaaa8888888888888888888888888aaaa", "Luric", new ImageIcon(getClass().getResource("/test/msg01.jpg")));
        addItemRight("378474747474747474", new ImageIcon(getClass().getResource("/test/msg01.jpg")));
        addDate("29/05/2025");
        addItemLeft("Hello anh ban chao anh ban nha aaaaaaaaaaaaa", "Luric", new ImageIcon(getClass().getResource("/test/ava01.jpg")));
        addItemRight("378474747474747474", new ImageIcon(getClass().getResource("/test/ava01.jpg")));
        addItemLeft("", "Luric", new ImageIcon(getClass().getResource("/test/ava01.jpg")));
    }

    private void init() {
        body.setLayout(new MigLayout("fillx", "", "5[]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }

    public void addItemLeft(String text, String user, Icon ...images) {
        ChatLeftWithProfile item = new ChatLeftWithProfile();
        item.setText(text);
        item.setImage(images);
        item.setTime();
        item.setUserProfile(user);
        body.add(item, "wrap, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }
    
    public void addItemRight(String text, Icon ...images) {
        ChatRight item = new ChatRight();
        item.setText(text);
        item.setImage(images);
        body.add(item, "wrap, al right, w 100::80%");
        //  ::80% set max with 80%
        body.repaint();
        body.revalidate();
    }

    public void addDate(String date){
        ChatDate item = new ChatDate();
        item.setDate(date);
        body.add(item, "wrap, al center");
        body.repaint();
        body.revalidate();
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
