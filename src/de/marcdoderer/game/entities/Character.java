package de.marcdoderer.game.entities;

import de.marcdoderer.game.utils.Sprite;

import java.io.IOException;

/**
 * new Character with every walk and jump Sprite
 */
public class Character {
    private Sprite[][] sprites;
    private Sprite[] addonImages;
    private int addon;

    /**
     * load every sprite of a character and every addon.
     * @param name Character name for folder structure
     * @param addons Names of the addons for folder structure
     * @param delay Milliseconds between the frames while walking
     * @param jumpDelay Milliseconds between the frames while jumping
     * @param walkOffset Offset how many pictures should be only played once while walking
     * @param jumpOffset Offset how many pictures should be only played once while jumping
     * @param addonOffset Offset how many pictures should be only played once while for the addons
     * @throws IOException Thrown if Sprite Image couldnot be loaded
     */
    public Character(String name, String[] addons, int delay, int jumpDelay, int walkOffset, int jumpOffset, int addonOffset) throws IOException {
        this.addon = 0;
        this.addonImages = new Sprite[addons.length];
        sprites = new Sprite[addons.length + 1][];
        for(int i = 0; i < addons.length; i++){
            sprites[i] = new Sprite[4];
            sprites[i][0] = new Sprite("rsc/ninja/"+ name + addons[i] + "/links", delay,walkOffset);
            sprites[i][1] = new Sprite("rsc/ninja/" + name + addons[i] + "/rechts", delay,walkOffset);
            sprites[i][2] = new Sprite("rsc/ninja/jump/"+ name + addons[i] + "/links", jumpDelay,jumpOffset);
            sprites[i][3] = new Sprite("rsc/ninja/jump/" + name + addons[i] + "/rechts", jumpDelay,jumpOffset);
        }
        for(int i = 1; i < addonImages.length; i++){
            addonImages[i] = new Sprite("rsc/addOn/" + addons[i], delay,addonOffset);
        }

    }

    /**
     * Returns the current addon sprite.
     * Null if no addon is selected.
     * @return addonImages[addon]
     */
    public Sprite getAddonSprite(){
        return addonImages[addon];
    }

    /**
     * Menu only! leftWalk-animation.
     * Stops the current Addon-animation change it and starts the new animation.
     * @param direction indicating of left(-1) or right(1) arrow is pressed to choose addon
     */
    public void changeAddon(int direction) {
        this.getWalkLeft().stopSprite();
        if(addon != 0)
            this.addonImages[addon].stopSprite();

        if (direction == 1) {
            addon = (addon < addonImages.length - 1) ? (addon += direction) : 0;
        }else{
            addon = (addon > 0) ? (addon += direction) : addonImages.length - 1;
        }
        this.getWalkLeft().startSprite();
        if(addon != 0)
            this.addonImages[addon].startSprite();
    }


    public Sprite getWalkLeft(){
        return this.sprites[addon][0];
    }
    public Sprite getWalkRight(){
        return this.sprites[addon][1];
    }
    public Sprite getJumpLeft(){
        return this.sprites[addon][2];
    }
    public Sprite getJumpRight(){
        return this.sprites[addon][3];
    }
}
