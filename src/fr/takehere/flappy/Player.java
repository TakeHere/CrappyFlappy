package fr.takehere.flappy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Player extends Actor{
    private static Player instance = null;

    final float initialSpeed = 10f;
    final float initialJumpForce = 13f;

    public boolean jump = false;
    public boolean keyPressed = false;

    private Player(Point spawnLoc, Image image) {
        super(spawnLoc, image, false, true);
    }

    public static Player get(){
        if (instance == null){
            try {
                Image playerImage = ImageIO.read(Flappy.getInstance().getClass().getResource("ressources/player.png"));
                Dimension imageSize = new Dimension(playerImage.getWidth(null), playerImage.getHeight(null));

                Image scaledImage = playerImage.getScaledInstance(imageSize.width / 4, imageSize.height / 4, Image.SCALE_AREA_AVERAGING);

                instance = new Player(new Point(50,200), scaledImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }
}
