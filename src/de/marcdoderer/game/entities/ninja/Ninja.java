package de.marcdoderer.game.entities.ninja;

import de.marcdoderer.game.entities.Character;
import de.marcdoderer.game.entities.Enitity;
import de.marcdoderer.game.entities.ninja.skill.Skill;
import de.marcdoderer.game.utils.Sprite;
import de.marcdoderer.game.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Ninja extends Enitity {

    private Character character;
    private Sprite currentSprite;

    private int startY;
    private double[] parable;

    private Skill[] skills;

    private boolean jumpable;
    private int jumpSpeed;

    private boolean watchLeft;

    private int untouchable;

    public enum STATE{
        WALK,
        JUMP,
        DOUBLE_JUMP,
    }

    private STATE state;

    public Ninja(Character character, int x, int y, int wallRight, int jumpHeight){
        super(x, y);
        init();
        this.character = character;
        this.setSprite(1);
        int gap = wallRight - x;
        this.parable = Utils.PARABEL_RECHNEN(x, this.y,
                (x + (gap / 2)) - currentSprite.getSprite().getWidth(), this.y - jumpHeight,
                wallRight - currentSprite.getSprite().getWidth(), this.y);
    }

    private void setSprite(int speedUp){
        if(this.currentSprite != null) this.stopSprite();
        if(state == STATE.JUMP || this.state == STATE.DOUBLE_JUMP)
            this.currentSprite = (this.watchLeft) ? this.character.getJumpLeft() : this.character.getJumpRight();
        else
            this.currentSprite = (this.watchLeft) ? this.character.getWalkLeft() : this.character.getWalkRight();

        if(speedUp > 1)
            this.currentSprite.speedUp(speedUp);

        this.startSprite();
    }

    private void jump(){
        if(this.state == STATE.DOUBLE_JUMP || !jumpable) return;
        this.state = (this.state == STATE.JUMP) ? STATE.DOUBLE_JUMP : STATE.JUMP;
        this.watchLeft = !this.watchLeft;
        this.setSprite(1);
    }

    public void startSprite(){
        this.currentSprite.startSprite();
    }

    public void stopSprite(){
        this.currentSprite.stopSprite();
    }

    public void setSkills(Skill[] skills){
        this.skills = skills;
    }

    public void jumpOff(int wallX){
        this.state = STATE.WALK;
        int speed = 1;
        for(Skill s: skills){
            if(s.getActivated()){
                speed = s.getAnimationSpeed();
                break;
            }
        }
        this.setSprite(speed);
        this.x = (watchLeft) ? wallX : wallX - this.currentSprite.getSprite().getWidth();
        this.y = this.startY;
    }

    public int getStartY(){
        return this.startY;
    }

    public STATE getState(){
        return this.state;
    }

    public void activateSkill(int time, int index){
        if(skills == null) return;
        if(index >= skills.length) return;

        skills[index].activateSkill(time);
    }

    public void setUntouchable(int duration){
        this.untouchable = duration;
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

    public void setY(int y){
        this.y = y;
    }

    public void setJumpable(boolean jumpable){
        this.jumpable = jumpable;
    }

    public int getY(){
        return this.y;
    }

    public int getUntouchable() {
        return untouchable;
    }

    public void slowDown(){
        this.currentSprite.normalDelay();
        this.state = STATE.WALK;
    }

    public void update(){
         if(untouchable != 0)
            this.untouchable--;

        switch(this.state){
            case JUMP:
            case DOUBLE_JUMP:
                this.y = (int) (this.parable[0] * Math.pow(x, 2) + this.parable[1] * x + this.parable[2]);
                this.x = (!this.watchLeft) ? this.x + this.jumpSpeed : this.x - this.jumpSpeed;
                break;
            default:
                break;
        }

        if(this.skills == null) return;

        for(Skill s : skills){
            if(s.getActivated()){
                s.update();
                break;
            }
        }
    }

    public void render(Graphics g){
        g.drawImage(currentSprite.getSprite(), this.x, this.y, null);
        g.drawRect(this.x, this.y, this.currentSprite.getSprite().getWidth(), this.currentSprite.getSprite().getWidth());
    }

    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                if(this.jumpable)
                    jump();
                break;
            default:
                break;
        }
    }

    private void init(){
        this.jumpSpeed = 15;
        this.untouchable = 0;
        this.startY = this.y;
        this.watchLeft = true;
        this.jumpable = true;
        this.state = STATE.WALK;
        this.skills  = null;

    }
}
