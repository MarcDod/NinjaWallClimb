package de.marcdoderer.game.entities.ninja.skill;

import de.marcdoderer.game.entities.ninja.Ninja;
import de.marcdoderer.game.ingame.Wall;

public class Rampage extends Skill {

    private int walkSpeed;
    private int highestPoint;

    public Rampage(Ninja ninja) {
        super(ninja, 2);
        init();
    }

    @Override
    public void update() {
        super.update();
        if(this.duration == 0){
            this.activated = false;
            this.ninja.setJumpable(true);
            ninja.slowDown();
            this.ninja.setY(ninja.getStartY());

            Wall.setSpeed(Wall.getNormalSpeed());

            return;
        }

        int backDuration = (ninja.getStartY() - this.highestPoint) / walkSpeed;
        if(ninja.getY() > highestPoint && !(this.duration < backDuration + 1)){

            this.ninja.setY(this.ninja.getY() - walkSpeed);

        }else if(ninja.getY() < ninja.getStartY() && this.duration < backDuration){

            this.ninja.setY(this.ninja.getY() + walkSpeed);

        }
    }

    @Override
    public void activateSkill(int duration) {
        super.activateSkill(duration);
        int maxHighestPoint = 100;
        this.highestPoint = ((ninja.getStartY() - (duration * walkSpeed) / 2) > maxHighestPoint) ? (ninja.getStartY() - (duration * walkSpeed) / 2) : maxHighestPoint;
        ninja.setJumpable(false);
        ninja.setUntouchable(duration + 50);

        Wall.setSpeed(Wall.getNormalSpeed() + walkSpeed);
    }

    private void init(){
        this.walkSpeed = 5;
        this.highestPoint = 0;
        this.animationSpeed = 2;
    }

}
