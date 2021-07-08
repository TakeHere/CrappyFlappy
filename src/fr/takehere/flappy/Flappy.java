package fr.takehere.flappy;

import fr.takehere.flappy.display.DeathPane;
import fr.takehere.flappy.display.GameFrame;
import fr.takehere.flappy.display.GamePane;
import lc.kra.system.keyboard.GlobalKeyboardHook;
import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.TimerTask;

public class Flappy {
    private static Flappy instance;
    final float initialGravity = 0.2f;
    final float initialJumpForce = 5f;
    final int spaceBetweenPipes = 130;

    public final boolean debug = false;
    public int score;

    boolean alreadyPressed;

    public static void main(String[] args) throws IOException {
        instance = new Flappy();

        //Init spikes
        Image spikeUpImage = ImageIO.read(Flappy.getInstance().getClass().getResource("ressources/spikeUp.png"));
        Image spikeDownImage = ImageIO.read(Flappy.getInstance().getClass().getResource("ressources/spikeDown.png"));

        new Spike(0 , instance.divideScale(spikeUpImage, 8), true);
        new Spike(380, instance.divideScale(spikeDownImage, 8), false);
        Spike.sizeSpikes();

        //Draw Level
        new Object(new Color(56, 175, 4), new Point(0,400), new Dimension(500,20), true);
        new Object(new Color(101, 56, 9), new Point(0,420), new Dimension(500,80), true);

        GameFrame.get();


        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);
        keyboardHook.addKeyListener(new GlobalKeyAdapter() {

            @Override
            public void keyPressed(GlobalKeyEvent event) {
                if (!instance.alreadyPressed){
                    instance.alreadyPressed = true;
                    Player.get().jump = true;
                }
            }

            @Override
            public void keyReleased(GlobalKeyEvent event) {
                instance.alreadyPressed = false;
            }
        });
    }


    long launchTime = System.currentTimeMillis();
    long lastTime = System.currentTimeMillis();
    int fps = 0;
    float minDeltaTime = 13.33333f;
    float maxDeltaTime = 100f;

    public void gameLoop() {
        Player player = Player.get();

        long currentTime = System.currentTimeMillis();
        float deltaTime = ( currentTime -  lastTime) / 1000.0f;

        if ( deltaTime < minDeltaTime )
            deltaTime = minDeltaTime;
        else if ( deltaTime > maxDeltaTime )
            deltaTime = maxDeltaTime;
        lastTime = currentTime;

        float gravity = initialGravity * deltaTime / minDeltaTime;
        float jumpForce = initialJumpForce * deltaTime / minDeltaTime;
        float spikeSpeed = Spike.initialSpeed * deltaTime / minDeltaTime;

        if (player.jump){
            player.jump = false;
            player.yVelocity = jumpForce * -1;
        }

        for (Spike spike : Spike.spikes){
            spike.xVelocity = spikeSpeed * -1;
            if (spike.location.x <= spike.size.width * -1){
                spike.location = new Point(spike.spawnLocation.x, spike.spawnLocation.y);

                Spike.sizeSpikes();

                if (spike.isUp){
                    score++;
                    playScoreSound();
                }
            }
        }

        for (Actor actor : Actor.getActors()){
            if (actor.gravity){
                actor.yVelocity += gravity;
            }

            actor.location.y += actor.yVelocity;
            actor.location.x += actor.xVelocity;

            actor.transform.x = actor.location.x;
            actor.transform.y = actor.location.y;
        }

        for (Object killerObject : Object.killerObjects){
            killerObject.transform.setLocation(killerObject.location.x, killerObject.location.y);
            killerObject.transform.setSize(killerObject.size.width, killerObject.size.height);

            if (player.transform.intersects(killerObject.transform)){
                GamePane.get().setVisible(false);
                GameFrame.get().setContentPane(DeathPane.get());

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            restartApplication();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },1000);
                return;
            }
        }

        fps++;
        if (System.currentTimeMillis() - launchTime > 1000){
            GameFrame.get().setTitle(GameFrame.title + " | fps: " + fps);
            launchTime += 1000;
            fps = 0;
        }

        try {
            Thread.sleep((long) deltaTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       GamePane.get().repaint();
    }

    public void restartApplication() throws IOException {
        StringBuilder cmd = new StringBuilder();
        cmd.append(System.getProperty("java.home") + File.separator + "bin" + File.separator + "java ");
        for (String jvmArg : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
            cmd.append(jvmArg + " ");
        }
        cmd.append("-cp ").append(ManagementFactory.getRuntimeMXBean().getClassPath()).append(" ");
        cmd.append(Flappy.class.getName()).append(" ");
        Runtime.getRuntime().exec(cmd.toString());
        System.exit(0);
    }

    public void playScoreSound(){
        try {
            AudioInputStream scoreSound = AudioSystem.getAudioInputStream(Flappy.getInstance().getClass().getResource("ressources/score.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(scoreSound);
            clip.start();

        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public Image divideScale(Image image, int division){
        Dimension imageSize = new Dimension(image.getWidth(null), image.getHeight(null));
        Image scaledImage = image.getScaledInstance(imageSize.width / division, imageSize.height / division, Image.SCALE_AREA_AVERAGING);
        return scaledImage;
    }

    public static Flappy getInstance() {
        return instance;
    }
}
