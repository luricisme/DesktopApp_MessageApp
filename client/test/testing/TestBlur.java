package testing;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import views.swing.blurHash.BlurHash;

public class TestBlur {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("D:\\Code\\Project\\Desktop_MessageApp\\MessageApp\\resources\\test\\ava01.jpg"));
            String blurhashStr = BlurHash.encode(image);
            System.out.println(blurhashStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
