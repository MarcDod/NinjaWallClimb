package de.marcdoderer.game.entities.ninja.skill;

import de.marcdoderer.game.entities.ninja.Ninja;

public abstract class Skill {

    protected int duration;
    protected Ninja ninja;
    protected int animationSpeed;

    protected boolean activated;

    public Skill(Ninja ninja, int animationSpeed){
        this.duration = 0;
        this.ninja = ninja;
        this.animationSpeed = animationSpeed;
        this.activated = false;
    }

    public void update(){
        this.duration--;
    }

    public int getAnimationSpeed(){
        return this.animationSpeed;
    }
    public void activateSkill(int duration){
        this.activated = true;
        this.duration = duration;
    }

    public boolean getActivated(){
        return this.activated;
    }
}
