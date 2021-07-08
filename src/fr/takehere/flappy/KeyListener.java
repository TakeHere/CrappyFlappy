package fr.takehere.flappy;

import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.TreeSet;

public class KeyListener implements java.awt.event.KeyListener {

    Set<Integer> pressedKeys = new TreeSet<Integer>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        Integer val = Integer.valueOf(code);
        if (pressedKeys.contains(val)) {
            //we've already pressed the key and it is being held down
            return;
        }
        else {
            System.out.println("Jump mon, reuf !");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
}
