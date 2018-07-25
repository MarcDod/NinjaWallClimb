package de.marcdoderer.game.states;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class State {

    protected StateManager stm;

    public State(StateManager stm){
        this.stm = stm;
        init();
    }

    /**
     * Initialize all items.
     */
    public abstract  void init();

    /**
     * Update this state every 16ms.
     */
    public abstract void update();

    /**
     * Render this state every 16ms.
     * If update needs to much time it do not render
     * @param g
     */
    public abstract void render(Graphics2D g);


    public abstract void keyPressed(KeyEvent e);
    public abstract void keyReleased(KeyEvent e);
    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void actionPerformed(String ID);
    public abstract void cleanUp();
    public abstract void revert();

}
