package de.marcdoderer.game.ingame;

import de.marcdoderer.game.entities.Character;
import de.marcdoderer.game.entities.ninja.Ninja;
import de.marcdoderer.game.entities.ninja.skill.Rampage;
import de.marcdoderer.game.entities.ninja.skill.Skill;
import de.marcdoderer.game.entities.obstacle.Balcony;
import de.marcdoderer.game.entities.obstacle.ObstacleManager;
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
    private ObstacleManager obstacleManager;

    private boolean pause;
    private int dmgTaken;

    /**
     *  Initialize a new Ninja with his skills and starts its animation sprite.
     * @param stm StateManager which is controlling the States.
     * @param character the character sprites of the ninja.
     */
    public Game(StateManager stm, Character character) {
        super(stm);
        this.ninja = new Ninja(character,this.walls[0].getX() + this.walls[0].getWidth(), Gui.SCREEN_HEIGHT / 2 + Gui.SCREEN_HEIGHT / 6, walls[walls.length - 1].getX(), 30);

        Skill[] skills = new Skill[1];
        skills[0] = new Rampage(ninja);
        this.ninja.setSkills(skills);

        ninja.startSprite();
    }

    /**
     *  If the ninja hits the wall it gets out of his jump;
     */
    private void manageNinja(){
        if(ninja.getState() != Ninja.STATE.JUMP && ninja.getState() != Ninja.STATE.DOUBLE_JUMP) return;
        if (ninja.hit
                (new int[]{walls[walls.length - 1].getX(), 0, walls[walls.length - 1].getWidth(), Gui.SCREEN_HEIGHT}))
            ninja.jumpOff(walls[walls.length - 1].getX());
        else if(ninja.hit(new int[]{walls[0].getX(), 0, walls[0].getWidth(), Gui.SCREEN_HEIGHT}))
            ninja.jumpOff(walls[0].getX() + walls[0].getWidth());
    }

    /**
     * pause the ninja, the background and all obstacle;
     */
    private void pause(){
        this.ninja.pauseSprite();
        this.background.pauseSprite();
        this.obstacleManager.pause();
    }

    @Override
    public void update() {
        if(this.pause) return;

        if(this.dmgTaken != 0)
            this.dmgTaken--;


        this.ninja.update();
        manageNinja();

        for(Wall w: this.walls){
            w.update();
        }

        this.obstacleManager.update();
        if(this.obstacleManager.lookForHit(this.ninja))
            this.dmgTaken += 5;


    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(this.background.getSprite(), 0, 0, null);
        g.drawImage(this.background.getSprite(), this.background.getSprite().getWidth(), 0, null);

        for(Wall w: this.walls){
            w.render(g);
        }

        this.obstacleManager.render(g);

        this.ninja.render(g);


        g.setColor(Color.green);
        g.fillRect(0, Gui.SCREEN_HEIGHT - 5, this.ninja.getUntouchable(), 5);


        if(dmgTaken == 0) return;

        g.setColor(new Color(255, 0, 0, 30));
        g.fillRect(0, 0, Gui.SCREEN_WIDTH, Gui.SCREEN_HEIGHT);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!this.pause)
            this.ninja.keyPressed(e);

        switch(e.getKeyCode()){
            case KeyEvent.VK_BACK_SPACE:
                this.stm.popState();
                break;
            case KeyEvent.VK_ESCAPE:
                this.pause = !this.pause;
                if(!this.pause)
                    revert();
                else
                    pause();
                break;
            case KeyEvent.VK_F:
                this.ninja.activateSkill(300, 0);
                break;
            default:
                break;
        }
    }

    @Override
    public void cleanUp() {
        this.ninja.stopSprite();
        this.background.stopSprite();
        this.obstacleManager.cleanUp();
    }

    @Override
    public void revert() {
        this.ninja.startSprite();
        this.background.startSprite();
        this.obstacleManager.resume();
    }


    @Override
    public void init() {
        this.pause = false;
        this.dmgTaken = 0;
        obstacleManager = new ObstacleManager();

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

            // Load Obstacles
            Sprite[] balconySpritesLeft =  Utils.loadObstacleSprite("balcony","left");
            Sprite[] balconySpritesRight =  Utils.loadObstacleSprite("balcony","right");

            // add Obstacles
            ObstacleManager.addNewObstacle(new Balcony(balconySpritesLeft, this.walls[0].getX() + this.walls[0].getWidth(), true), 3);
            ObstacleManager.addNewObstacle(new Balcony(balconySpritesRight, this.walls[this.walls.length - 1].getX() - balconySpritesRight[0].getSprite().getWidth(), false), 3);

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
