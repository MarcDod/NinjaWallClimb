package de.marcdoderer.game.ingame.obstacle;

import de.marcdoderer.game.entities.Entity;
import de.marcdoderer.game.entities.ninja.Ninja;
import de.marcdoderer.game.utils.Sprite;


public abstract class Obstacle extends Entity {

    protected Sprite[] spritePool;
    protected boolean isAlive;
    protected int fallSpeed;
    protected boolean destroyed;

    private float spawnProbability;

    public Obstacle(Sprite[] spritePool, int x, int fallSpeed, int percent){
        super(x, 0);
        init();
        this.fallSpeed = fallSpeed;
        this.setSpawnProbability(percent);
        this.spritePool = new Sprite[spritePool.length];

        for(int i = 0; i < this.spritePool.length; i++){
            this.spritePool[i] = spritePool[i].getInstanceOf();
        }

        this.sprite = this.spritePool[(int)(Math.random() * spritePool.length - 1)];
        this.y = -sprite.getSprite().getHeight();
    }

    public void startObstacle(){
        this.isAlive = true;
        this.setSpawnTime(0);
    }

    public void setFallSpeed(int fallSpeed){
        this.fallSpeed = fallSpeed;
    }

    protected void reset(){
        this.sprite.stopSprite();
        this.sprite = this.spritePool[ (int)(Math.random() * spritePool.length - 1) ];
        this.y = -this.sprite.getSprite().getWidth();
        this.isAlive = false;
        this.destroyed = false;

    }

    public void setSpawnProbability(int percent){
        this.spawnProbability = percent / 100.0f;
    }

    public float getSpawnProbability(){
        return this.spawnProbability;
    }

    public void pauseSprite(){
        this.sprite.pauseSprite();
    }

    public void returnSprite(){
        if(this.destroyed) this.sprite.startSprite();
    }

    public abstract Obstacle getInstanceOf();
    public abstract int getTimeSinceLastSpawn();
    public abstract void setSpawnTime(int time);
    public abstract int getSpawnDistance();

    /**
     * if the obstacle got hit by the ninja the obstacle gets destroyed and an the obstacle even will be played
     * @param ninja the player
     * @return true, if the ninja gets DMG : else false
     */
    public boolean hit(Ninja ninja){
        boolean hit = ninja.hit(new int[]{this.x, this.y, this.sprite.getSprite().getWidth(), this.sprite.getSprite().getHeight()});
        if(!hit || this.destroyed)
            return false;

        this.destroy();
        this.destroyed = true;

        return hitEvent(ninja);
    }

    protected abstract boolean hitEvent(Ninja ninja);

    protected void destroy(){
        this.sprite.startSprite();
    }

    public boolean getIsAlive(){
        return this.isAlive;
    }

    private void init(){
        this.isAlive = false;
        this.destroyed = false;
    }
}
