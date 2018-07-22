package de.marcdoderer.game.ingame;

import de.marcdoderer.game.entities.Character;
import de.marcdoderer.game.entities.ninja.Ninja;
import de.marcdoderer.game.entities.ninja.skill.Rampage;
import de.marcdoderer.game.entities.ninja.skill.Skill;
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

    private boolean pause;

    public Game(StateManager stm, Character character) {
        super(stm);
        this.ninja = new Ninja(character,this.walls[0].getX() + this.walls[0].getWidth(), Gui.SCREEN_HEIGHT / 2 + Gui.SCREEN_HEIGHT / 6, walls[walls.length - 1].getX(), 30);

        Skill[] skills = new Skill[1];
        skills[0] = new Rampage(ninja);
        this.ninja.setSkills(skills);

        ninja.startSprite();
    }

    @Override
    public void update() {

        if(pause) return;

        ninja.update();

        for(Wall w: walls){
            w.update();
        }



        if(ninja.getState() != Ninja.STATE.JUMP && ninja.getState() != Ninja.STATE.DOUBLE_JUMP) return;
        if (ninja.hit
                (new int[]{walls[walls.length - 1].getX(), 0, walls[walls.length - 1].getWidth(), Gui.SCREEN_HEIGHT}))
            ninja.jumpOff(walls[walls.length - 1].getX());
        else if(ninja.hit(new int[]{walls[0].getX(), 0, walls[0].getWidth(), Gui.SCREEN_HEIGHT}))
            ninja.jumpOff(walls[0].getX() + walls[0].getWidth());

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background.getSprite(), 0, 0, null);
        g.drawImage(background.getSprite(), background.getSprite().getWidth(), 0, null);

        for(Wall w: walls){
            w.render(g);
        }

        this.ninja.render(g);


        g.setColor(Color.green);
        g.fillRect(0, Gui.SCREEN_HEIGHT - 5, ninja.getUntouchable(), 5);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!pause)
            ninja.keyPressed(e);

        switch(e.getKeyCode()){
            case KeyEvent.VK_BACK_SPACE:
                stm.popState();
                break;
            case KeyEvent.VK_ESCAPE:
                pause = !pause;
                if(!pause)
                    revert();
                else
                    cleanUp();
                break;
            case KeyEvent.VK_F:
                ninja.activateSkill(300, 0);
                break;
            default:
                break;
        }
    }

    @Override
    public void cleanUp() {
        ninja.stopSprite();
        background.stopSprite();
    }

    @Override
    public void revert() {
        ninja.startSprite();
        background.startSprite();
    }

    @Override
    public void init() {
        this.pause = false;

        try{
            this.background = new Sprite("rsc/wallpaper/blueCity", 80,0);
            background.startSprite();
            BufferedImage[] walls = Utils.loadPictures("rsc/wallpaper/wall");
            Wall.loadWalls(walls);
            this.walls = new Wall[6];
            for(int i = 0; i < this.walls.length; i++){
                int x = (i < this.walls.length / 2) ? 0 : Gui.SCREEN_WIDTH - this.walls[i - 1].getWidth();
                int y = (i < this.walls.length / 2) ? i : i - 3;
                this.walls[i] = new Wall(x, (y - 1) * (Gui.SCREEN_HEIGHT / 2));
            }
        }catch(IllegalArgumentException | IOException e){
            e.printStackTrace();
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
}
