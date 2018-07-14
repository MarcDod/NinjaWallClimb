package de.marcdoderer.game.menu;

import de.marcdoderer.game.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * creates a prototype for buttons and textfields.
 */
public abstract class MenuItem {

    protected int x, y;
    protected int width, height;
    protected BufferedImage[] images;
    boolean selected;

    /**
     * set the position of the item and load there Images
     * @param x
     * @param y
     * @param path
     * @throws IOException
     */
    public MenuItem(int x,int y, String path) throws IOException{
        this.x = x;
        this.y = y;
        loadImages(path);
        this.width = images[0].getWidth();
        this.height = images[0].getHeight();
    }

    private void loadImages(String path) throws IOException {
        images = Utils.loadPictures(path);
    }

    public abstract void render(Graphics g);

    /**
     * returns true, if the X/Y - parameters hit the Item.
     * @param x
     * @param y
     * @return
     */
    protected boolean isOn(int x, int y){
        return x >= this.x && x <= this.x + width
                && y >= this.y && y <= this.y + height;
    }

    /**
     * select the item if smbdy click it.
     * @param e
     */
    protected void mousePressed(MouseEvent e){
        if(isOn(e.getX(), e.getY())){
            selected = true;
        }
    }
}
