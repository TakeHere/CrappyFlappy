package fr.takehere.flappy;

import fr.takehere.flappy.display.GameFrame;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Spike extends Actor{
    final static float initialSpeed = 3;

    public Point spawnLocation;
    public Dimension initialSize;
    public boolean isUp;

    public static List<Spike> spikes = new ArrayList<>();

    public Spike(int spawnY, Image image, boolean isUp) {
        super(new Point(GameFrame.get().getWidth() + image.getWidth(null), spawnY)
                , image, true, false);
        this.spawnLocation = new Point(this.location.x, this.location.y);
        this.isUp = isUp;
        this.initialSize = new Dimension(this.size.width, this.size.height);

        spikes.add(this);
    }

    public static void sizeSpikes(){
        Spike up = spikes.get(0);
        Spike down = spikes.get(1);

        up.size.height = new Random().nextInt(GameFrame.get().getHeight() / 2 - 50 + 1) + 50;
        down.size.height = GameFrame.get().getHeight() - (up.size.height + Flappy.getInstance().spaceBetweenPipes);

        down.location.y = GameFrame.get().getHeight() - down.size.height;
    }
}
