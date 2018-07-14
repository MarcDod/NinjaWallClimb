package de.marcdoderer.game.states;

import de.marcdoderer.game.menu.Menu;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Stack;

/**
 * Manage the State´s.
 */
public class StateManager {

    private Stack<State> states;

    /**
     * Initialize a new Stack and pushes a new Menu.
     */
    public StateManager(){

        states = new Stack<State>();
        states.push(new Menu(this));
    }

    /**
     * Update the state on top of the Stack.
     */
    public void update(){
        states.peek().update();
    }

    /**
     * Render the state on top of the Stack.
     * @param g
     */
    public void render(Graphics g){
        states.peek().render(g);
    }

    public void keyPressed(KeyEvent e){
        states.peek().keyPressed(e);
    }

    public void keyReleased(KeyEvent e){
        states.peek().keyReleased(e);
    }

    public void mousePressed(MouseEvent e){
        states.peek().mousePressed(e);
    }
    public void mouseReleased(MouseEvent e){
        states.peek().mouseReleased(e);
    }

    /**
     * CleanUp the old State and Pushes in a new State
     * @param state
     */
    public void pushState(State state){
        this.states.peek().cleanUp();
        this.states.push(state);
    }

    /**
     * CleanUp and Delete the State on top of the Stack
     * Returns the Stack below
     */
    public void popState(){
        this.states.peek().cleanUp();
        this.states.pop();
        this.states.peek().ressume();
    }


}