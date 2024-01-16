package main.java;

import javax.swing.*;
import java.awt.*;

public class Game extends  JFrame{

    public Game() {
        Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\griffinl\\Documents\\icon.png");
        this.setIconImage(icon);

        this.add(new Graphics());
        this.setTitle("Snake Game");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);



    }
}
