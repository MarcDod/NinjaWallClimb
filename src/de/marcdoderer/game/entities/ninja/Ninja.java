package de.marcdoderer.game.entities.ninja;

import de.marcdoderer.game.entities.Character;
import de.marcdoderer.game.entities.Entity;
import de.marcdoderer.game.entities.ninja.skill.Skill;
import de.marcdoderer.game.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Ninja extends Entity {

    private Character character;

    private int startY;
    private double[] parable;

    private Skill[] skills;

    private boolean jumpable;
    private int jumpSpeed;

    private boolean watchLeft;

    private int untouchable;

    private int life;

    public enum STATE{
        WALK,
        JUMP,
        DOUBLE_JUMP,
    }

    private STATE state;

    public Ninja(Character character, int x, int y, int wallRight, int jumpHeight){
        super(x, y);
        init();
        this.character = character.getInstanceOf();
        this.setSprite(1);
        int gap = wallRight - x;
        this.parable = Utils.PARABEL_RECHNEN(x, this.y,
                (x + (gap / 2)) - sprite.getSprite().getWidth(), this.y - jumpHeight,
                wallRight - sprite.getSprite().getWidth(), this.y);
    }

    private void setSprite(int speedUp){
        if(this.sprite != null) this.stopSprite();
        if(state == STATE.JUMP || this.state == STATE.DOUBLE_JUMP)
            this.sprite = (this.watchLeft) ? this.character.getJumpLeft() : this.character.getJumpRight();
        else
            this.sprite = (this.watchLeft) ? this.character.getWalkLeft() : this.character.getWalkRight();

        if(speedUp > 1)
            this.sprite.speedUp(speedUp);

        this.startSprite();
    }

    private void jump(){
        if(this.state == STATE.DOUBLE_JUMP || !jumpable) return;
        this.state = (this.state == STATE.JUMP) ? STATE.DOUBLE_JUMP : STATE.JUMP;
        this.watchLeft = !this.watchLeft;
        this.setSprite(1);
    }

    /**
     * if the ninja is not untouchable life gets decreased
     * @param dmg dmg witch the ninja takes
     * @return return true if life got decreased else false;
     */
    public boolean takeDMG(int dmg){
        if(this.untouchable != 0) return false;
        //this.life -= dmg;
        return true;
    }

    public void startSprite(){
        this.sprite.startSprite();
    }

    public void pauseSprite() { this.sprite.pauseSprite(); }

    public void stopSprite(){
        this.sprite.stopSprite();
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
        this.x = (watchLeft) ? wallX : wallX - this.sprite.getSprite().getWidth();
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
        if (this.x <= hitbox[0] + hitbox[2] && x + this.sprite.getSprite().getWidth()
                >= hitbox[0] && this.y + this.sprite.getSprite().getHeight() >= hitbox[1] && this.y
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
        this.sprite.normalDelay();
        this.state = STATE.WALK;
    }

    @Override
    public void update(){
        if(this.life == 0){
            return;
        }

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

    @Override
    public void render(Graphics2D g){
        g.drawImage(sprite.getSprite(), this.x, this.y, null);
        g.drawRect(this.x, this.y, this.sprite.getSprite().getWidth(), this.sprite.getSprite().getWidth());
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
        this.life = 3;
    }
}
