package de.marcdoderer.game.entities;

public abstract class Enitity {
    protected int x, y;
    protected int width, height;

    public Enitity(int x, int y){
        this.x = x;
        this.y = y;
    }
}
