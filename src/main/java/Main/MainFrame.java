package Main;

import javax.swing.*;

public class MainFrame extends JFrame {
    ImageIcon icon = new ImageIcon("src/main/java/Assets/icons/cat_icon.png");
    MainFrame() {
        MainPanel mainPanel = new MainPanel();
        this.add(mainPanel);
        this.setTitle("Tamagacha");
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
