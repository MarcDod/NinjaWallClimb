package de.marcdoderer.game.ingame.obstacle;

import de.marcdoderer.game.entities.ninja.Ninja;
import de.marcdoderer.game.main.Gui;
import de.marcdoderer.game.utils.Sprite;

import java.awt.*;

public class Balcony extends Obstacle{

    private static int timeSinceLastSpawnLeft = 0;
    private static int timeSinceLastSpawnRight = 0;
    private static int spawnDistance = 0;

    private boolean onLeftSide;

    public Balcony(Sprite[] spritePool, int x, boolean onLeftSide) {
        super(spritePool, x, 10, 2);
        this.init();
        this.onLeftSide = onLeftSide;
    }

    // Private :

    // Public :

    public Obstacle getInstanceOf(){
        return new Balcony(this.spritePool, this.x, this.onLeftSide);
    }

    @Override
    public void setSpawnTime(int time){
        if(this.onLeftSide)
            Balcony.timeSinceLastSpawnLeft = time;
        else
            Balcony.timeSinceLastSpawnRight = time;
    }

    @Override
    public int getSpawnDistance(){
        return Balcony.spawnDistance;
    }

    @Override
    public int getTimeSinceLastSpawn() {
        if(this.onLeftSide)
            return Balcony.timeSinceLastSpawnLeft;
        else
            return Balcony.timeSinceLastSpawnRight;
    }

    @Override
    protected boolean hitEvent(Ninja ninja) {
        boolean dmg = ninja.takeDMG(1);
        if(dmg) ninja.setUntouchable(this.sprite.getSprite().getHeight() / fallSpeed);
        return dmg;
    }

    @Override
    public void update() {
        this.y += fallSpeed;

        if(this.y > Gui.SCREEN_HEIGHT + this.sprite.getSprite().getHeight()){
            this.isAlive = false;
            this.reset();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(this.sprite.getSprite(), x, y, null);
    }

    private void init(){
        Balcony.spawnDistance = (sprite.getSprite().getHeight() / this.fallSpeed) * 6;
        Balcony.timeSinceLastSpawnRight = spawnDistance;
        Balcony.timeSinceLastSpawnLeft = spawnDistance;
    }
}
