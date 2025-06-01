package views.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import javax.swing.Icon;
import javax.swing.JButton;

public class OptionButton extends JButton {
    public OptionButton() {
        setContentAreaFilled(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    public void setSelected(boolean bln){
        super.setSelected(bln);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        if(isSelected()){
            g.setColor(new Color(110, 213, 255));
            g.fillRect(0, getHeight() - 2, getWidth(), getHeight());
        }
    } 
}
