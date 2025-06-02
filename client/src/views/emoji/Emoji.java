package views.emoji;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class Emoji {
    private static Emoji instance;
    
    public static Emoji getInstance(){
        if(instance == null){
            instance = new Emoji();
        }
        return instance;
    }
    
    private Emoji(){
        
    }
    
    public List<EmojiModel> getStyle1(){
        List<EmojiModel> list = new ArrayList<>();
        for(int i = 1; i <= 20; i++){
            list.add(new EmojiModel(i, new ImageIcon(getClass().getResource("/emoji/" + i + ".png"))));
        }
        return list;
    }
    
    public EmojiModel getEmoji(int id){
        return new EmojiModel(id, new ImageIcon(getClass().getResource("/emoji/" + id + ".png")));
    }
}
