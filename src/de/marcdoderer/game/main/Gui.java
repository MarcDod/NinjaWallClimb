package de.marcdoderer.game.main;

import de.marcdoderer.handler.KeyHandler;
import de.marcdoderer.handler.MouseHandler;
import de.marcdoderer.game.states.StateManager;

import javax.swing.*;
import java.awt.*;

/**
 * Graphical user Interface. JFrame and a GameLoop panel.
 */
public class Gui {

    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 600;

    private JFrame frame;

    /**
     * creates a new JFrame and add the GameLoop panel.
     * Adds a new KeyListener and MouseListener.
     * @param title
     * @param stm
     * @param gamePanel
     */
    public Gui(String title, StateManager stm, GameLoop gamePanel){


        frame = new JFrame(title);
        frame.setSize(Gui.SCREEN_WIDTH, Gui.SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        //frame.setUndecorated(true);
        frame.setVisible(true);



        gamePanel.setBounds(0, 0, Gui.SCREEN_WIDTH, Gui.SCREEN_HEIGHT);
        gamePanel.setPreferredSize(new Dimension(Gui.SCREEN_WIDTH, Gui.SCREEN_HEIGHT));
        gamePanel.setMaximumSize(new Dimension(Gui.SCREEN_WIDTH, Gui.SCREEN_HEIGHT));
        gamePanel.setMinimumSize(new Dimension(Gui.SCREEN_WIDTH, Gui.SCREEN_HEIGHT));
        gamePanel.setFocusable(false);
        gamePanel.setVisible(true);

        MouseHandler mh = new MouseHandler(stm);
        gamePanel.addMouseListener(mh);
        frame.addKeyListener(new KeyHandler(stm));

        frame.add(gamePanel);
        frame.pack();
    }

}
