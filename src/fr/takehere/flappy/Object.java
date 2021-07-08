package fr.takehere.flappy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Object{
    public Color color;
    public Dimension size;
    public Point location;
    public Image image;
    public Rectangle transform;
    public static List<Object> objects = new ArrayList<>();
    public static List<Object> killerObjects = new ArrayList<>();

    public Object(Color color, Point spawnLoc, Dimension size, boolean killer) {
        this.color = color;
        this.size = size;
        this.location = spawnLoc;
        this.transform = new Rectangle(spawnLoc, size);

        if (killer) killerObjects.add(this);

        this.objects.add(this);
    }

    public Object(Image image, Point spawnLoc, boolean killer) {
        this.size = new Dimension(image.getWidth(null), image.getHeight(null));
        this.location = spawnLoc;
        this.image = image;
        this.transform = new Rectangle(spawnLoc, this.size);

        if (killer) killerObjects.add(this);

        this.objects.add(this);
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
