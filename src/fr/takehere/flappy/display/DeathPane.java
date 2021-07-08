package fr.takehere.flappy.display;

import fr.takehere.flappy.Flappy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.text.AttributedString;

public class DeathPane extends JPanel {

    private static DeathPane instance;

    private DeathPane() {
        this.setFocusable(false);
        this.setLayout(new GridBagLayout());

        JLabel score = new JLabel();
        score.setForeground(Color.BLUE);
        score.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
        score.setText("<HTML><U>" + "Score: " + Flappy.getInstance().score + "</U></HTML>");

        this.add(score);
    }

    public static DeathPane get(){
        if (instance == null){
            instance = new DeathPane();
        }
        return instance;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;

        try {
            Image deathImage = ImageIO.read(Flappy.getInstance().getClass().getResource("ressources/death.png"));
            g2d.drawImage(deathImage, 0, 0, 500, 500, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        super.paintComponents(g);
    }
}
