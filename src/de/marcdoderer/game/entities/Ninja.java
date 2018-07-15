package de.marcdoderer.game.entities;

import de.marcdoderer.game.utils.Sprite;
import de.marcdoderer.game.utils.Utils;

import java.awt.*;

public class Ninja extends Enitity {

    private Character character;
    private Sprite currentSprite;

    private double[] parable;

    private int jumpSpeed;

    private boolean watchLeft;

    public enum STATE{
        WALK,
        JUMP,
        DOUBLE_JUMP
    }

    private STATE state;

    public Ninja(Character character, int x, int y, int wallLeft, int wallRight){
        super(x, y);
        init();
        this.character = character;
        this.setSprite();
        int gap = wallRight - wallLeft;
        this.parable = Utils.PARABEL_RECHNEN(wallLeft, this.y,
                (wallLeft + (gap / 2)) - currentSprite.getSprite().getWidth(), this.y - 30,
                wallRight - currentSprite.getSprite().getWidth(), this.y);
        this.state = STATE.WALK;
    }

    private void setSprite(){
        if(this.currentSprite != null) this.stopSprite();
        if(state == STATE.JUMP || this.state == STATE.DOUBLE_JUMP)
            this.currentSprite = (this.watchLeft) ? this.character.getJumpLeft() : this.character.getJumpRight();
        else
            this.currentSprite = (this.watchLeft) ? this.character.getWalkLeft() : this.character.getWalkRight();
        this.startSprite();
    }

    public void startSprite(){
        this.currentSprite.startSprite();
    }

    public void stopSprite(){
        this.currentSprite.stopSprite();
    }


    public void jump(){
        if(this.state == STATE.DOUBLE_JUMP) return;
        this.state = (this.state == STATE.JUMP) ? STATE.DOUBLE_JUMP : STATE.JUMP;
        this.watchLeft = !this.watchLeft;
        this.setSprite();
    }

    public void jumpOff(){
        this.state = STATE.WALK;
        this.setSprite();
    }

    public STATE getState(){
        return this.state;
    }
    public boolean hit(int[] hitbox) {

        boolean temp = false;
        if (this.x <= hitbox[0] + hitbox[2] && x + this.currentSprite.getSprite().getWidth()
                >= hitbox[0] && this.y + this.currentSprite.getSprite().getHeight() >= hitbox[1] && this.y
                <= hitbox[1] + hitbox[3]) {
            temp = true;
        }
        return temp;
    }


    public void update(){
        if(this.state == STATE.JUMP || this.state == STATE.DOUBLE_JUMP){
            this.y = (int) (this.parable[0] * Math.pow(x, 2) + this.parable[1] * x + this.parable[2]);
            this.x = (!this.watchLeft) ? this.x + this.jumpSpeed : this.x - this.jumpSpeed;
        }
    }

    public void render(Graphics g){
        g.drawImage(currentSprite.getSprite(), this.x, this.y, null);
        g.drawRect(this.x, this.y, this.currentSprite.getSprite().getWidth(), this.currentSprite.getSprite().getWidth());
    }

    private void init(){
        this.jumpSpeed = 15;
        this.watchLeft = true;
    }
}
