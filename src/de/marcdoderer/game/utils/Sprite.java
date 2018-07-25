package de.marcdoderer.game.utils;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Creates a new animation.
 */
public class Sprite {
    private BufferedImage[] sprites;
    private int loopOffset;
    private int currentPicture;
    private Timer t;
    private int delay;

    /**
     * loads every Image of the animation and creates a timer for the animation.
     */
    public Sprite(String path, int spriteDelay, int loopOffset) throws IllegalArgumentException, IOException{
        this.loopOffset = loopOffset;
        sprites = Utils.loadPictures(path);
        this.delay = spriteDelay;
        this.t = new Timer(this.delay, actionEvent ->  changeSprite());
    }

    private Sprite(BufferedImage[] sprites, int spriteDelay, int loopOffset){
        this.loopOffset = loopOffset;
        this.sprites = sprites;
        this.delay = spriteDelay;
        this.t = new Timer(this.delay, actionEvent ->  changeSprite());
    }

    /**
     * Change the Image to the next Image.
     */
    private void changeSprite(){
        System.out.println("h");
        currentPicture = (currentPicture < sprites.length - 1) ? (currentPicture += 1) : loopOffset;
    }

    /**
     * Stops the animation and reset the sprite
     */
    public void stopSprite(){
        if(!t.isRunning()) return;
        t.stop();
        setCurrentPicture(0);
    }

    /**
     * Stops the animation without reset the animation.
     */
    public void pauseSprite(){
        if(!t.isRunning()) return;
        this.t.stop();
    }

    public void speedUp(int delay){
        t.setDelay(this.delay / delay);
    }

    public void normalDelay(){
        t.setDelay(this.delay);
    }

    /**
     * Starts the animation
     */
    public void startSprite(){
        if(t.isRunning()) return;
        t.start();
    }

    public Sprite getInstanceOf(){
        t.stop();
        return new Sprite(this.sprites, this.delay, this.loopOffset);
    }

    /**
     * Set the current animation if currentPicture is valid.
     * @param currentPicture the picture to be the current picture after invoking this method
     * @throws IllegalArgumentException thrown if picture number is not in range
     */
    public void setCurrentPicture(int currentPicture) throws  IllegalArgumentException{
        if(currentPicture > sprites.length || currentPicture < 0)
            throw new IllegalArgumentException("Picture number not valid for sprite");

        this.currentPicture = currentPicture;
    }

    public int getCurrentPicture() {
        return currentPicture;
    }

    /**
     * returns the current Image of the sprite.
     * @return the current image of the sprite
     */
    public BufferedImage getSprite(){
        return sprites[currentPicture];
    }
}
