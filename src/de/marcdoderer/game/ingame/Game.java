package de.marcdoderer.game.ingame;

import de.marcdoderer.game.entities.Character;
import de.marcdoderer.game.entities.Ninja;
import de.marcdoderer.game.states.State;
import de.marcdoderer.game.states.StateManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Game extends State {

    private Ninja ninja;

    public Game(StateManager stm, Character character) {
        super(stm);
        this.ninja = new Ninja(character, 100, 100);
    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
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
    }

    @Override
    public void ressume() {

    }
}
