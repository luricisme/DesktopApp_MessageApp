package views.emoji;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class EmojiModel {

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    
    public EmojiModel(){
        
    }

    public EmojiModel(int id, Icon icon) {
        this.id = id;
        this.icon = icon;
    }
    
    int id;
    Icon icon;
    
    public EmojiModel toSize(int x, int y){
        return new EmojiModel(id, new ImageIcon(((ImageIcon)icon).getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH)));
    }
}
