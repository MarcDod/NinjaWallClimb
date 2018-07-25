package de.marcdoderer.game.ingame;


import de.marcdoderer.game.main.Gui;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wall {
    private static BufferedImage[] walls;
    private static int normalSpeed = 10;
    private static int speed = normalSpeed;

    private int x, y;
    private BufferedImage currentImage;

    public static void setSpeed(int speed){
        Wall.speed = speed;
    }
    public static int getNormalSpeed(){
        return Wall.normalSpeed;
    }

    public Wall(int x,int y){
        this.x = x;
        this.y = y;
        this.currentImage = walls[(int) ( Math.random()* walls.length)];
    }

    public void render(Graphics g){
        g.drawImage(this.currentImage, this.x, this.y, null);
    }

    public void update(){

        this.y += speed;

        if(this.y > Gui.SCREEN_HEIGHT){
            this.y = (-Gui.SCREEN_HEIGHT / 2) + speed;
            this.currentImage = walls[(int) ( Math.random()* walls.length)];
        }
    }

    /**
     *
     * @return the width of the currentImage
     */
    public int getWidth(){
        return this.currentImage.getWidth();
    }
    public int getX(){ return this.x; }

    public static void loadWalls(BufferedImage[] walls){
        Wall.walls = walls;
    }


}
