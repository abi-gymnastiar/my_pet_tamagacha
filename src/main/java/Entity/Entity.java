package Entity;

import Main.GamePanel;

import java.awt.image.BufferedImage;

public class Entity {
    public GamePanel gp;
    public int x, y;

    public BufferedImage sprite;

    public Entity(GamePanel gp)
    {
        this.gp = gp;
    }
}