package de.marcdoderer.game.ingame;

import de.marcdoderer.game.entities.Character;
import de.marcdoderer.game.entities.Ninja;
import de.marcdoderer.game.main.Gui;
import de.marcdoderer.game.states.State;
import de.marcdoderer.game.states.StateManager;
import de.marcdoderer.game.utils.Sprite;
import de.marcdoderer.game.utils.Utils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Game extends State {

    private Ninja ninja;
    private Sprite background;
    private Wall walls[];

    public Game(StateManager stm, Character character) {
        super(stm);
        this.ninja = new Ninja(character, 100, 100);
    }

    @Override
    public void init() {
        try{
            this.background = new Sprite("rsc/wallpaper/blueCity", 80);
            background.startSprite();
            BufferedImage[] walls = Utils.loadPictures("rsc/wallpaper/wall");
            Wall.loadWalls(walls);
            this.walls = new Wall[6];
            for(int i = 0; i < this.walls.length; i++){
                int x = (i < this.walls.length / 2) ? 0 : Gui.SCREEN_WIDTH - this.walls[i - 1].getWidth();
                int y = (i < this.walls.length) ? i : i - 3;
                this.walls[i] = new Wall(x, (y - 1) * (Gui.SCREEN_HEIGHT / 2));
            }
        }catch(IllegalArgumentException | IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        for(Wall w: walls){
            w.update();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background.getSprite(), 0, 0, null);
        g.drawImage(background.getSprite(), background.getSprite().getWidth(), 0, null);

        for(Wall w: walls){
            w.render(g);
        }

        this.ninja.render(g);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyChar()){
            case KeyEvent.VK_BACK_SPACE:
                stm.popState();
                break;
            case KeyEvent.VK_SPACE:
                ninja.startSprite();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void actionPerformed(String ID) {

    }

    @Override
    public void cleanUp() {
        ninja.stopSprite();
        background.stopSprite();
    }

    @Override
    public void ressume() {

    }
}
