package de.marcdoderer.game.main;

import de.marcdoderer.game.states.StateManager;

import javax.swing.*;
import java.awt.*;

/**
 * Create a new Gui, draw on a JPanel.
 */
public class GameLoop extends JPanel implements Runnable {

    public static final int FPS = 60;
    public static final int MAX_LOOP_TIME = 1000 / FPS;


    private boolean running;
    private Gui gameFrame;

    private StateManager stm;

    /**
     * Initialize the StateManager and the Gui.
     */
    public GameLoop(){
        this.running = true;
        this.stm = new StateManager();

        this.gameFrame = new Gui("Game", stm, this);
    }

    /**
     * Initialize the GameLoop and starts it.
     * @param args
     */
    public static void main(String[] args){
        GameLoop game = new GameLoop();
        new Thread(game).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.clearRect(0, 0, Gui.SCREEN_WIDTH, Gui.SCREEN_HEIGHT);
        render(g);
    }

    /**
     * Run our gameloop if running = true.
     */
    @Override
    public void run() {
        long timestamp;                                                         // Zeit nach Update
        long oldTimestamp;                                                      // Zeit vor

        while(running){
            oldTimestamp = System.currentTimeMillis();
            update();                                                           // Updated das Game
            timestamp = System.currentTimeMillis();
            if(timestamp - oldTimestamp > MAX_LOOP_TIME){
                //System.out.println("Wir sind zu spät!");
                continue;
            }
            repaint();                                                           // Zeichnet das Spiel
            timestamp = System.currentTimeMillis();
            //System.out.println(MAX_LOOP_TIME + " : " + (timestamp-oldTimestamp));
            if(timestamp-oldTimestamp <= MAX_LOOP_TIME) {
                try {
                    Thread.sleep(MAX_LOOP_TIME - (timestamp-oldTimestamp) );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * update the current State.
     */
    private void update(){
        stm.update();
    }

    /**
     * render the current State
     * @param g
     */
    private void render(Graphics g){

        stm.render(g);
    }
}
