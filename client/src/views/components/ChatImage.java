package views.components;

import events.PublicEvent;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import models.FileSenderModel;
import models.ReceiveImageModel;
import net.miginfocom.swing.MigLayout;

public class ChatImage extends javax.swing.JPanel {

    public ChatImage(boolean right) {
        initComponents();
        setLayout(new MigLayout("", "0[" + (right ? "right" : "left") + "]0", "2[]2"));
    }

    public void addImage(FileSenderModel filesSender) {
        Icon image = new ImageIcon(filesSender.getFile().getAbsolutePath());
        ImageItem pic = new ImageItem();
        pic.setPreferredSize(getAutoSize(image, 200, 200));
        pic.setImage(image, filesSender);
        addEvent(pic, image);
        add(pic, "wrap");

    }

    public void addImage(ReceiveImageModel dataImage) {

        ImageItem pic = new ImageItem();
        pic.setPreferredSize(new Dimension(dataImage.getWidth(), dataImage.getHeight()));
        pic.setImage(dataImage);
//            addEvent(pic, image);
        add(pic, "wrap");

    }

    private void addEvent(Component com, Icon image) {
        com.setCursor(new Cursor(Cursor.HAND_CURSOR));
        com.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    PublicEvent.getInstance().getEventImageView().viewImage(image);
                }
            }
        });
    }

    private Dimension getAutoSize(Icon image, int w, int h) {
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.min(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        return new Dimension(width, height);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
