package de.marcdoderer.game.entities;

import de.marcdoderer.game.utils.Sprite;

import java.awt.*;

public class Ninja extends Enitity {

    private Character character;
    private Sprite currentSprite;

    public Ninja(Character character, int x, int y){
        super(x, y);
        this.character = character;
        this.setSprite(this.character.getWalkleft());
    }

    private void setSprite(Sprite sprite){
        if(currentSprite != null) this.stopSprite();
        this.currentSprite = sprite;
        this.startSprite();
    }

    public void startSprite(){
        this.currentSprite.startSprite();
    }

    public void stopSprite(){
        this.currentSprite.stopSprite();
    }

    public void render(Graphics g){
        g.drawImage(currentSprite.getSprite(), this.x, this.y, null);
    }
}
