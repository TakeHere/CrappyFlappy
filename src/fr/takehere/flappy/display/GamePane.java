package fr.takehere.flappy.display;

import fr.takehere.flappy.Flappy;
import fr.takehere.flappy.Object;
import fr.takehere.flappy.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class GamePane extends JPanel{
    Flappy flappy = Flappy.getInstance();
    private static GamePane instance;
    public static Image backgroundImage;

    private GamePane() {
        this.setFocusable(true);
        this.setRequestFocusEnabled(true);

        try {
            backgroundImage = ImageIO.read(Flappy.getInstance().getClass().getResource("ressources/background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GamePane get(){
        if (instance == null){
            instance = new GamePane();
        }
        return instance;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(10));

        //Draw background
        g2d.drawImage(backgroundImage, 0, 0, 1000, 1000, null);

        //Draw objects
        for (Object object : Object.objects){
            g2d.setColor(object.color);
            if (object.image != null){
                g2d.drawImage(object.image, object.location.x, object.location.y, object.size.width, object.size.height, null);
            }else {
                g2d.fillRect(object.location.x, object.location.y, object.size.width, object.size.height);
            }
        }

        //Draw Scores
        g2d.setColor(Color.black);
        g2d.drawString("Score: " + flappy.score, 20,20);

        //Debug
        if (flappy.debug){
            g2d.setColor(new Color(255, 0, 77, 176));
            g2d.drawRect(Player.get().transform.x, Player.get().transform.y, Player.get().transform.width, Player.get().transform.height);

            for (Object killerObject : Object.killerObjects){
                g2d.drawRect(killerObject.transform.x, killerObject.transform.y, killerObject.transform.width, killerObject.transform.height);
            }
        }

        super.paintComponents(g);
        flappy.gameLoop();
    }
}