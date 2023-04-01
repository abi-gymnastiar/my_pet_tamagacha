package Entity;

import Main.MainPanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Bowl extends Entity {
    public BufferedImage bowl_empty, bowl_full, sprite;
    //public int width, height;
    public String foodStatus;
    public Bowl(MainPanel mp, int petx, int pety) {
        super(mp);
        setBowlImages();
        setBowlFull();
        setInitialPosition(petx, pety);
    }

    public void setInitialPosition(int x, int y) {
        //x = 360;
        this.x = x;
        this.y = y;
        foodStatus = "full";
    }

    public void setBowlImages() {
        try {
            File file = new File("src/main/java/Assets/bowl/bowl_empty.png");
            bowl_empty = ImageIO.read(file);
            file = new File("src/main/java/Assets/bowl/bowl_full.png");
            bowl_full = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBowlFull () {
        sprite = bowl_full;
    }

    public void setBowlEmpty () {
        sprite = bowl_empty;
    }
}
