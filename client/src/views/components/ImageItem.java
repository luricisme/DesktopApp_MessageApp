package views.components;

import events.EventFileSender;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import models.FileSenderModel;
import models.ReceiveImageModel;
import views.swing.blurHash.BlurHash;

public class ImageItem extends javax.swing.JLayeredPane {
    public ImageItem() {
        initComponents();
    }
    
    // Handle for send image
    public void setImage(Icon image, FileSenderModel fileSender){
        fileSender.addEvent(new EventFileSender(){
            @Override
            public void onSending(double percentage) {
                progress.setValue((int)percentage);
            }

            @Override
            public void onStartSending() {
                
            }

            @Override
            public void onFinish() {
                progress.setVisible(false);
            }
            
        });
        pic.setImage(image);
    }
    
    public void setImage(ReceiveImageModel dataImage){
        int width = dataImage.getWidth();
        int height = dataImage.getHeight();
        int[] data = BlurHash.decode(dataImage.getImage(), width, height, 1);
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        img.setRGB(0, 0, width, height, data, 0, width);
        Icon icon = new ImageIcon(img);
        pic.setImage(icon);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pic = new views.swing.PictureBox();
        progress = new views.swing.Progress();

        pic.setLayout(new java.awt.GridBagLayout());

        progress.setToolTipText("");
        progress.setProgressType(views.swing.Progress.ProgressType.CANCEL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 34;
        gridBagConstraints.ipady = -104;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(28, 22, 20, 24);
        pic.add(progress, gridBagConstraints);

        setLayer(pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private views.swing.PictureBox pic;
    private views.swing.Progress progress;
    // End of variables declaration//GEN-END:variables
}
