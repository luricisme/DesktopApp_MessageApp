package views.components;

import java.awt.Color;
import javax.swing.Icon;

public class ChatLeftWithProfile extends javax.swing.JLayeredPane {

    public ChatLeftWithProfile() {
        initComponents();
        txt.setBackground(new Color(236,234,228));
    }
    
    public void setUserProfile(String user){
        txt.setUserProfile(user);
    }
    
    public void setImageProfile(Icon image){
        avaImage.setImage(image);
    }
    
    public void setText(String text){
        txt.setText(text);
        txt.setTime("10:30 PM"); // Testing
//        txt.sendSuccess();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();
        avaImage = new views.swing.ImageAvatar();
        txt = new views.components.ChatItem();

        setBackground(new java.awt.Color(244, 244, 244));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.LINE_AXIS));

        avaImage.setImage(new javax.swing.ImageIcon(getClass().getResource("/test/ava01.jpg"))); // NOI18N
        avaImage.setPreferredSize(new java.awt.Dimension(30, 30));

        jLayeredPane1.setLayer(avaImage, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane1Layout.createSequentialGroup()
                .addComponent(avaImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addGap(0, 1, Short.MAX_VALUE)
                .addComponent(avaImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        add(jLayeredPane1);
        add(txt);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private views.swing.ImageAvatar avaImage;
    private javax.swing.JLayeredPane jLayeredPane1;
    private views.components.ChatItem txt;
    // End of variables declaration//GEN-END:variables
}
