package de.marcdoderer.game.menu;

import de.marcdoderer.game.states.State;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Button extends MenuItem{

    private String buttonID;
    private State state;
    private boolean enable;

    public Button(int x, int y, String path, String buttonID, State state) throws IOException {
        super(x, y, path);
        this.buttonID = buttonID;
        this.state = state;
        this.enable = true;
    }

    public void enableButtom(){
        this.enable = true;
    }

    public void diableButton(){
        this.enable = false;
    }

    public void mouseReleased(MouseEvent e){
        if(this.isOn(e.getX(), e.getY())) {
            state.actionPerformed(this.buttonID);
            this.selected = false;
        }
    }

    @Override
    public void render(Graphics g) {
        int image = (this.selected) ? images.length - 1 : 0;
        if(enable)
            g.drawImage(this.images[image], x, y, null);
    }
}
