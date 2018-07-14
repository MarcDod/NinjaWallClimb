package de.marcdoderer.game.utils;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Creates a new animation.
 */
public class Sprite {
    private BufferedImage[] sprites;
    private int currentPicture;
    private Timer t;

    /**
     * loads every Image of the animation and creates a timer for the animation.
     */
    public Sprite(String path, int spriteDelay) throws IllegalArgumentException, IOException{
        sprites = Utils.loadPictures(path);
        this.t = new Timer(spriteDelay, actionEvent -> {
            changeSprite();
        });
    }


    /**
     * Change the Image to the next Image.
     */
    private void changeSprite(){
        currentPicture = (currentPicture < sprites.length - 1) ? (currentPicture += 1) : 0;
    }

    /**
     * Stops the animation.
     */
    public void stopSprite(){
        if(!t.isRunning()) return;
        t.stop();
    }

    /**
     * Starts the animation
     */
    public void startSprite(){
        if(t.isRunning()) return;
        t.start();
    }

    /**
     * Set the current animation if currentPicture is valid.
     * @param currentPicture
     * @throws IllegalArgumentException
     */
    public void setCurrentPicture(int currentPicture) throws  IllegalArgumentException{
        if(currentPicture > sprites.length)
            throw new IllegalArgumentException("Picture number not valid for sprite");

        this.currentPicture = currentPicture;
    }

    public int getCurrentPicture() {
        return currentPicture;
    }

    /**
     * returns the current Image of the sprite.
     * @return
     */
    public BufferedImage getSprite(){
        return sprites[currentPicture];
    }
}
