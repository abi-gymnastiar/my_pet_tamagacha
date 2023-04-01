package Entity;

import Main.MainPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Pet extends Entity implements Renderer, MouseListener, MouseMotionListener {
    BufferedImage sprite, idle1, idle2, walk1, walk2, walk3, walk4,
                    walkLeft1, walkLeft2, walkLeft3, walkLeft4,
                    idleLeft1, idleLeft2, grab, grabLeft, grab2,
                    grabLeft2;
    String direction, status;
    Random random = new Random();
    int speed, counter, spriteTick, mouseXOffset, mouseYOffset,
            petWidth, petHeight, xCollision, yCollision,
            fallingSpeed, xMouseGrab, yMouseGrab;
    int randomNum = 6;
    Bowl bowl;
    public Pet(MainPanel mp)
    {
        super(mp);
        getPetImage();
        setInitialConditions();
        bowl = new Bowl(this.mp, 360, (y+64));
        bowl.setBowlFull();
        mp.addMouseListener(this);
        mp.addMouseMotionListener(this);
    }

    public void setInitialConditions() {
        x = 0;
        y = mp.screenHeight - idle1.getHeight() - 40;
        speed = 1;
        status = "idle";
        spriteNum = 1;
        petWidth = idle1.getWidth();
        petHeight = idle1.getHeight();
        direction = "right";
        fallingSpeed = 7;
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
            file = new File("src/main/java/Assets/grab/cat_grab.png");
            grab = ImageIO.read(file);
            file = new File("src/main/java/Assets/grab/cat_grab_left.png");
            grabLeft = ImageIO.read(file);
            file = new File("src/main/java/Assets/grab/cat_grab2.png");
            grab2 = ImageIO.read(file);
            file = new File("src/main/java/Assets/grab/cat_grab_left2.png");
            grabLeft2 = ImageIO.read(file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void update() {
        xCollision = x+petWidth; yCollision = y+petHeight;
        //System.out.println(xCollision);
        counter++;
        //System.out.println(counter);
        if (counter % 30 == 0)
            tick++;
        spriteCounter++;
        if (spriteCounter > 30) {
            if (spriteNum == 1)  spriteNum = 2;
            else if (spriteNum == 2)  spriteNum = 3;
            else if (spriteNum == 3)  spriteNum = 4;
            else if (spriteNum == 4)  spriteNum = 1;
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
        if (status == "walk" && direction == "right" &&
                xCollision == bowl.x && bowl.foodStatus == "full") {
            status = "eat";
            tick = 0;
            randomNum = 20;
        }
        if (status == "eat" && tick == randomNum) {
            status = "walk";
            tick = 0;
            randomNum = random.nextInt(3, 7);
            bowl.setBowlEmpty();
            bowl.foodStatus = "empty";
        }
        if (status == "falling") {
            if (yCollision >= (mp.screenHeight - 40)) {
                status = "tpToFloorY";
            }
        }
        if (status == "tpToFloorY") {
            y = mp.screenHeight - (idle1.getHeight() + 40);
            tick = 0;
            randomNum = random.nextInt(5, 15);
            status = "idle";
        }
        //if pet goes beyond frame
        if (status == "idle" && x < 0) {
            direction = "right";
            status = "walk";
            tick = 0;
            randomNum = 7;
        }
        //if pet goes beyond frame
        if ((status == "idle" &&
                x+idle1.getHeight() > mp.screenWidth)) {
            direction = "left";
            status = "walk";
            randomNum = 7;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage sprite = null;

        switch (status) {
            case "idle":
                if (direction == "right") {
                    if (spriteNum == 1 || spriteNum == 3) { sprite = idle1; }
                    if (spriteNum == 2 || spriteNum == 4) { sprite = idle2; }
                } else if (direction == "left") {
                    if (spriteNum == 1 || spriteNum == 3) { sprite = idleLeft1; }
                    if (spriteNum == 2 || spriteNum == 4) { sprite = idleLeft2; }
                }
                break;
            case "walk":
                if (direction == "right") {
                    x += speed;
                    if(spriteNum == 1){ sprite = walk1; }
                    if(spriteNum == 2){ sprite = walk2; }
                    if(spriteNum == 3){ sprite = walk3; }
                    if(spriteNum == 4){ sprite = walk4; }
                    if (xCollision == mp.screenWidth) { direction = "left"; }
                } else if (direction == "left") {
                    x -= speed;
                    if(spriteNum == 1){ sprite = walkLeft1; }
                    if(spriteNum == 2){ sprite = walkLeft2; }
                    if(spriteNum == 3){ sprite = walkLeft3; }
                    if(spriteNum == 4){ sprite = walkLeft4; }
                    if (x == 0) { direction = "right"; }
                }
                break;
            case "eat":
                //sprite needs to be changed
                if (spriteNum == 1 || spriteNum == 3) { sprite = idle1; }
                if (spriteNum == 2 || spriteNum == 4) { sprite = idle2; }
                break;
            case "grab":
                //sprite needs to be changed
                x = xMouseGrab;
                y = yMouseGrab;
                if (direction == "right") {
                    if (spriteNum == 1 || spriteNum == 3) { sprite = grab; }
                    if (spriteNum == 2 || spriteNum == 4) { sprite = grab2; }
                }
                if (direction == "left") {
                    if (spriteNum == 1 || spriteNum == 3) { sprite = grabLeft; }
                    if (spriteNum == 2 || spriteNum == 4) { sprite = grabLeft2; }
                }
                break;
            case "falling":
                //sprite needs to be changed
                y += fallingSpeed;
                if (direction == "right") {
                    if (spriteNum == 1 || spriteNum == 3) { sprite = idle1; }
                    if (spriteNum == 2 || spriteNum == 4) { sprite = idle2; }
                } else if (direction == "left") {
                    if (spriteNum == 1 || spriteNum == 3) { sprite = idleLeft1; }
                    if (spriteNum == 2 || spriteNum == 4) { sprite = idleLeft2; }
                }
                break;
        }
        g2.drawImage(sprite, x, y, idle1.getWidth(), idle1.getHeight(), null);
        g2.drawImage(bowl.sprite, bowl.x, bowl.y, bowl.bowl_full.getWidth(), bowl.bowl_full.getHeight(), null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        //miaw when clicked
        if (mouseX >= x && mouseX <= x + idle1.getWidth() &&
                mouseY >= y && mouseY <= y + idle1.getHeight()) {
            System.out.println("miaw");
        }
        //fill the bowl
        if (bowl.foodStatus == "empty") {
            if (mouseX >= bowl.x && mouseX <= bowl.x + bowl.bowl_empty.getWidth() &&
                    mouseY >= bowl.y && mouseY <= bowl.y + bowl.bowl_empty.getHeight()) {
                System.out.println("mmm food...");
                bowl.sprite = bowl.bowl_full;
                bowl.foodStatus = "full";
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            //drag cat
            if (mouseX >= (x+32) && mouseX <= (x + idle1.getWidth()-32) &&
                    mouseY >= (y+32) && mouseY <= (y + idle1.getHeight()-32)
                    && direction == "right") {
                xMouseGrab = mouseX - 64;
                yMouseGrab = mouseY - 16;
                status = "grab";
            }
            if (mouseX >= (x+32) && mouseX <= (x + idle1.getWidth()-32) &&
                    mouseY >= (y+32) && mouseY <= (y + idle1.getHeight()-32)
                    && direction == "left") {
                xMouseGrab = mouseX - 64;
                yMouseGrab = mouseY - 16;
                status = "grab";
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && status == "grab") {
            if (yCollision < (mp.screenHeight - 40))
                status = "falling";
            else
                status = "tpToFloorY";
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        if (status == "grab") {
            xMouseGrab = mouseX - 64;
            yMouseGrab = mouseY - 16;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
