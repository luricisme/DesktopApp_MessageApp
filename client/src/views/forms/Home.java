package views.forms;

import models.UserAccountModel;
import net.miginfocom.swing.MigLayout;

public class Home extends javax.swing.JLayeredPane {
    private Chat chat;
    public Home() {
        initComponents();
        init();
    }
    
    private void init(){
        // | margin | [col1] | gap | [col2] | gap | [col3] | margin |
        // !: Cố định không thay đổi được
        // 0[fill]0: Cách hàng hoạt động lề trên|lấp đầy theo chiều dọc|lề dưới
        setLayout(new MigLayout("fillx, filly", "0[200!]5[fill, 100%]5[200!]0", "0[fill]0")); 
        this.add(new Menu_Left());
        chat = new Chat();
        this.add(chat);
        this.add(new Menu_Right());
        chat.setVisible(false);
    }
    
    public void setUser(UserAccountModel user){
        chat.setUser(user);
        chat.setVisible(true);
    }
    
    public void updateUser(UserAccountModel user){
        chat.updateUser(user);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 479, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
