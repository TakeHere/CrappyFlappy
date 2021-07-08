package fr.takehere.flappy;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Actor extends Object {
    float yVelocity = 0;
    float xVelocity = 0;
    boolean gravity;

    static List<Actor> actors = new ArrayList<>();

    public Actor(Point spawnLoc, Image image, boolean killer, boolean gravity) {
        super(image, spawnLoc, killer);
        this.gravity = gravity;

        actors.add(this);
    }

    public Actor(Point spawnLoc, Dimension size, Color color, boolean killer, boolean gravity){
        super(color, spawnLoc, size, killer);
        this.gravity = gravity;

        actors.add(this);
    }

    public static List<Actor> getActors() {
        return actors;
    }
}