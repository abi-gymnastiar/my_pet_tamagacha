package Entity;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pet extends Entity implements Renderer, MouseListener {

    public Pet(GamePanel gp)
    {
        super(gp);

        setInitialCoordinate();
        getPetImage();
        gp.addMouseListener(this);
    }

    public void setInitialCoordinate() {
        x = 0;
        y = 0;
    }

    public void getPetImage()
    {
        try {
            File file = new File("src/main/java/Assets/cate.png");
            sprite = ImageIO.read(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update() {

    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage bufferedImage = sprite;
        g2.drawImage(bufferedImage, x, y,null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseX >= x && mouseX <= x + sprite.getWidth() &&
                mouseY >= y && mouseY <= y + sprite.getHeight()) {
            System.out.println("miaw");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
