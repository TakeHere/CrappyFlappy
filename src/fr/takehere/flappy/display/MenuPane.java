package fr.takehere.flappy.display;

import fr.takehere.flappy.Flappy;
import fr.takehere.flappy.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class MenuPane extends JPanel {

    private static MenuPane instance;

    private MenuPane(){
        this.setBackground(Color.BLACK);
        this.setFocusable(false);
        this.setLayout(new GridBagLayout());

        JButton startButton = new JButton();
        startButton.setBackground(new Color(42, 203, 195));
        startButton.setForeground(Color.BLACK);
        startButton.setText("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 50));
        startButton.setPreferredSize(new Dimension(200,100));
        startButton.setFocusable(false);
        this.add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MenuPane.get().setVisible(false);
                GameFrame.get().setContentPane(GamePane.get());
            }
        });

        this.addKeyListener(new KeyListener() {
            boolean alreadyPressed;

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (!alreadyPressed){
                    alreadyPressed = true;
                    Player.get().jump = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                alreadyPressed = false;
            }
        });
    }

    public static MenuPane get(){
        if (instance == null){
            instance = new MenuPane();
        }
        return instance;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        Image menuImage = null;
        try {
            menuImage = ImageIO.read(Flappy.getInstance().getClass().getResource("ressources/menu.png"));
            g2d.drawImage(menuImage, 0, 0, 500, 500, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.paintComponents(g);
    }
}
