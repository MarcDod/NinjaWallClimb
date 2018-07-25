package de.marcdoderer.game.entities;

import de.marcdoderer.game.utils.Sprite;

import java.awt.*;

public abstract class Entity {
    protected int x, y;
    protected Sprite sprite;

    public Entity(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract void update();

    public abstract  void render(Graphics2D g);
}
