package views.forms;

import events.EventLogin;
import events.EventMessage;
import events.PublicEvent;
import io.socket.client.Ack;
import models.LoginModel;
import models.MessageModel;
import models.RegisterModel;
import models.UserAccountModel;
import services.Service;

public class Login extends javax.swing.JPanel {

    public Login() {
        initComponents();
        init();
    }

    private void init() {
        PublicEvent.getInstance().addEventLogin(new EventLogin() {
            @Override
            public void login(LoginModel data) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Login");
                        PublicEvent.getInstance().getEventMain().showLoading(true);
                        Service.getInstance().getClient().emit("login", data.toJSONObject(), new Ack() {
                            @Override
                            public void call(Object... os) {
                                if (os.length > 0) {
                                    boolean action = (Boolean) os[0];
                                    if (action) {
                                        Service.getInstance().setUser(new UserAccountModel(os[1]));
                                        PublicEvent.getInstance().getEventMain().showLoading(false);
                                        PublicEvent.getInstance().getEventMain().initChat();
                                    } else {
                                        // Password wrong
                                        PublicEvent.getInstance().getEventMain().showLoading(false);
                                    }
                                } else {
                                    PublicEvent.getInstance().getEventMain().showLoading(false);
                                }
                            }

                        });
                    }

                }).start();
            }

            @Override
            public void register(RegisterModel data, EventMessage message) {
                System.out.println("Register");
                System.out.println("Data send: " + data.getUserName() + " Pass: " + data.getPassword());
                Service.getInstance().getClient().emit("register", data.toJsonObject(), new Ack() {
                    @Override
                    public void call(Object... os) {
                        if (os.length > 0) {
                            MessageModel msg = new MessageModel((boolean) os[0], os[1].toString());
                            if (msg.isAction()) {
                                UserAccountModel user = new UserAccountModel(os[2]);
                                Service.getInstance().setUser(user);
                            }
                            message.callMessage(msg);
                            // Call message back when done register
                        }
                    }
                });
            }

            @Override
            public void goRegister() {
                slide.show(1);
            }

            @Override
            public void goLogin() {
                slide.show(0);
            }

        });
        P_Login login = new P_Login();
        P_Register register = new P_Register();
        slide.init(login, register);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pic = new views.swing.PictureBox();
        appName = new javax.swing.JLabel();
        appLine = new javax.swing.JLabel();
        slide = new views.swing.PanelSlide();

        setBackground(new java.awt.Color(255, 255, 255));

        pic.setImage(new javax.swing.ImageIcon(getClass().getResource("/icons/login_image01.png"))); // NOI18N

        appName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        appName.setForeground(new java.awt.Color(126, 125, 125));
        appName.setText("Chat Application");

        pic.setLayer(appName, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout picLayout = new javax.swing.GroupLayout(pic);
        pic.setLayout(picLayout);
        picLayout.setHorizontalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(appName)
                .addContainerGap(389, Short.MAX_VALUE))
        );
        picLayout.setVerticalGroup(
            picLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(picLayout.createSequentialGroup()
                .addGap(490, 490, 490)
                .addComponent(appName))
        );

        appLine.setBackground(new java.awt.Color(102, 153, 255));
        appLine.setOpaque(true);

        slide.setBackground(new java.awt.Color(255, 255, 255));
        slide.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout slideLayout = new javax.swing.GroupLayout(slide);
        slide.setLayout(slideLayout);
        slideLayout.setHorizontalGroup(
            slideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 256, Short.MAX_VALUE)
        );
        slideLayout.setVerticalGroup(
            slideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 372, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(appLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(slide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(slide, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)))
                .addComponent(appLine, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appLine;
    private javax.swing.JLabel appName;
    private views.swing.PictureBox pic;
    private views.swing.PanelSlide slide;
    // End of variables declaration//GEN-END:variables
}
