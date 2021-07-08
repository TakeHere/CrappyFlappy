package fr.takehere.flappy.display;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private static GameFrame instance;
    public static String title = "Crappy Flappy !";

    private GameFrame() throws HeadlessException {
        super(title);
        this.setSize(500,500);
        this.setLayout(new GridBagLayout());
        this.setContentPane(MenuPane.get());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setResizable(false);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screenSize.width - this.getWidth()) / 2, (screenSize.height - this.getHeight()) / 2);
    }

    public static GameFrame get(){
        if (instance == null){
            instance = new GameFrame();
        }
        return instance;
    }
}
