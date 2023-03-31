package Entity;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Pet extends Entity implements Renderer, MouseListener {
    BufferedImage sprite, idle1, idle2, walk1, walk2, walk3, walk4,
                    walkLeft1, walkLeft2, walkLeft3, walkLeft4,
                    idleLeft1, idleLeft2;
    String direction, status;
    Random random = new Random();
    int speed, counter, spriteTick,
            petWidth, petHeight, xCollision, yCollision;
    int randomNum = 6;
    public Pet(GamePanel gp)
    {
        super(gp);
        getPetImage();
        setInitialCoordinate();
        gp.addMouseListener(this);
    }

    public void setInitialCoordinate() {
        x = 0;
        y = 0;
        speed = 1;
        status = "idle";
        spriteNum = 1;
        petWidth = idle1.getWidth();
        petHeight = idle1.getHeight();
        direction = "right";
    }

    public void getPetImage()
    {
        try {
            File file = new File("src/main/java/Assets/idle/idle_1.png");
            idle1 = ImageIO.read(file);
            file = new File("src/main/java/Assets/idle/idle_2.png");
            idle2 = ImageIO.read(file);
            file = new File("src/main/java/Assets/idle/idle_left_1.png");
            idleLeft1 = ImageIO.read(file);
            file = new File("src/main/java/Assets/idle/idle_left_2.png");
            idleLeft2 = ImageIO.read(file);
            file = new File("src/main/java/Assets/walk/walk_1.png");
            walk1 = ImageIO.read(file);
            file = new File("src/main/java/Assets/walk/walk_2.png");
            walk2 = ImageIO.read(file);
            file = new File("src/main/java/Assets/walk/walk_3.png");
            walk3 = ImageIO.read(file);
            file = new File("src/main/java/Assets/walk/walk_4.png");
            walk4 = ImageIO.read(file);
            file = new File("src/main/java/Assets/walk/walk_left_1.png");
            walkLeft1 = ImageIO.read(file);
            file = new File("src/main/java/Assets/walk/walk_left_2.png");
            walkLeft2 = ImageIO.read(file);
            file = new File("src/main/java/Assets/walk/walk_left_3.png");
            walkLeft3 = ImageIO.read(file);
            file = new File("src/main/java/Assets/walk/walk_left_4.png");
            walkLeft4 = ImageIO.read(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        xCollision = x+petWidth; yCollision = y+petHeight;
        System.out.println(xCollision);
        counter++;
        //System.out.println(counter);
        if (counter % 30 == 0)
            tick++;
        spriteCounter++;
        if (spriteCounter > 30) {
            if (spriteNum == 1)
                spriteNum = 2;
            else if (spriteNum == 2)
                spriteNum = 3;
            else if (spriteNum == 3)
                spriteNum = 4;
            else if (spriteNum == 4)
                spriteNum = 1;
            spriteCounter = 0;
        }

        //System.out.println(tick);
        if (status == "idle" && tick == randomNum) {
            status = "walk";
            tick = 0;
            randomNum = random.nextInt(3, 7);
        }
        if (status == "walk" && tick == randomNum) {
            status = "idle";
            tick = 0;
            randomNum = random.nextInt(10, 30);
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage sprite = null;

        switch (status) {
            case "idle":
                if (direction == "right") {
                    if (spriteNum == 1 || spriteNum == 3) {
                        sprite = idle1;
                    }
                    if (spriteNum == 2 || spriteNum == 4) {
                        sprite = idle2;
                    }
                } else if (direction == "left") {
                    if (spriteNum == 1 || spriteNum == 3) {
                        sprite = idleLeft1;
                    }
                    if (spriteNum == 2 || spriteNum == 4) {
                        sprite = idleLeft2;
                    }
                }
                break;
            case "walk":
                if (direction == "right") {
                    x += speed;
                    if(spriteNum == 1){
                        sprite = walk1;
                    }
                    if(spriteNum == 2){
                        sprite = walk2;
                    }
                    if(spriteNum == 3){
                        sprite = walk3;
                    }
                    if(spriteNum == 4){
                        sprite = walk4;
                    }
                    if (xCollision == gp.screenWidth) {
                        direction = "left";
                    }
                } else if (direction == "left") {
                    x -= speed;
                    if(spriteNum == 1){
                        sprite = walkLeft1;
                    }
                    if(spriteNum == 2){
                        sprite = walkLeft2;
                    }
                    if(spriteNum == 3){
                        sprite = walkLeft3;
                    }
                    if(spriteNum == 4){
                        sprite = walkLeft4;
                    }
                    if (x == 0) {
                        direction = "right";
                    }
                }
                break;
        }
        g2.drawImage(sprite, x, y, idle1.getWidth(), idle1.getHeight(), null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseX >= x && mouseX <= x + idle1.getWidth() &&
                mouseY >= y && mouseY <= y + idle1.getHeight()) {
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
