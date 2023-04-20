package Entity;

import Main.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Platform extends Entity{
    public BufferedImage sprite;
    public int width, height;
    public Platform(MainPanel mp, int x, int y) {
        super(mp);
        this.x = x;
        this.y = y;
        setInit();
    }

    public void setInit() {
        //set sprite here from assets
        File file = new File("src/main/java/Assets/platform/platform.png");
        try {
            this.sprite = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        width = sprite.getWidth()*16;
        height = sprite.getHeight()*2;
    }

    //draw function
    public void draw(Graphics2D g2) {
        g2.drawImage(sprite, x, y, width, height, null);
    }
}
