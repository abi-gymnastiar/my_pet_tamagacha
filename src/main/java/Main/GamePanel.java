package Main;

import Entity.Pet;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    public int screenWidth = 480;
    public int screenHeight = 480;
    final int fps = 60;
    Thread gameThread;

    Pet pet = new Pet(this);

    GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    public void gameStartThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null)
        {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1)
            {
                //UPDATE
                update();
                //DRAW SCREEN
                repaint();

                delta--;
                drawCount++;
            }
        }
    }

    public void update()
    {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g.setColor(Color.blue);
        g.fillRect(0, 0, screenWidth, screenHeight);

        pet.draw(g2);
    }
}
