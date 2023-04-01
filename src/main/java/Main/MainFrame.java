package Main;

import javax.swing.*;

public class MainFrame extends JFrame {
    MainFrame() {
        MainPanel mainPanel = new MainPanel();
        this.add(mainPanel);
        this.setTitle("Tamagacha");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
