package abiansyah;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame implements ActionListener {
    JButton button; //to make it global
    MyFrame() {
        this.setTitle("Petta");
        this.setSize(480, 480);

        //image icon
        ImageIcon pet = new ImageIcon("src/main/java/abiansyah/cate.png");
        JLabel label = new JLabel();
        label.setIcon(pet);
        label.setHorizontalAlignment(JLabel.CENTER);

        //buttons
        button = new JButton("miaw");
        button.setPreferredSize(new Dimension(100, 50));
        button.addActionListener(this);
        button.setForeground(Color.white);
        button.setBackground(Color.darkGray);
        //button.addActionListener(e -> System.out.println("miaw")); //using lambda expression

        JPanel panel = new JPanel();
        panel.setBackground(Color.lightGray);
        panel.setLayout(new BorderLayout());
        // Add the centerPanel to center of JFrame
        add(panel);

        panel.add(label, BorderLayout.CENTER);
        panel.add(button, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button)
        {
            System.out.println("miaw");
        }
    }
}

