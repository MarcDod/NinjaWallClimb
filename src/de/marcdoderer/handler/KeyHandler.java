package de.marcdoderer.handler;

import de.marcdoderer.game.states.StateManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    private StateManager stm;

    public KeyHandler(StateManager stm){
        this.stm = stm;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        stm.keyPressed(keyEvent);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        stm.keyReleased(keyEvent);
    }
}
