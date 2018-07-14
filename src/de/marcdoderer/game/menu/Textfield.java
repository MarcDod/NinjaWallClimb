package de.marcdoderer.game.menu;




import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * Creates new Textfield.
 */
public class Textfield extends  MenuItem{

    private String text;
    private Font font;
    private Color c;

    /**
     * Inititalize the textfield.
     * @param x
     * @param y
     * @param path
     * @param startText
     * @param font
     * @param color
     * @throws IOException
     */
    public Textfield(int x, int y, String path, String startText, Font font, Color color) throws IOException {
        super(x, y, path);
        this.font = font;
        this.c = color;
        this.text = startText;
    }

    public String getText(){
        return this.text;
    }

    /**
     * writes a text, if textfield is selected.
     * Enf or backslash to delete the last char.
     *
     * Select/ Unselect the textfield if Enter is pressed.
     * @param ke
     * @return
     */
    public boolean keyPressed(KeyEvent ke){
        String allowed_character = "([abcdefghijklmnopqrstuvwxyzöäü ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ1234567890ß])*";
        switch(ke.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                this.selected = !selected;
                break;
            case KeyEvent.VK_DELETE:
            case KeyEvent.VK_BACK_SPACE:
                if (!selected || text.length() <= 0) {
                    break;
                }
                text = text.substring(0, text.length() - 1);
                break;
            default:
                if (!selected || !(text.length() < 20)) {
                    break;
                }
                if (!String.valueOf(ke.getKeyChar()).matches(allowed_character) || text.length() == 0 && ke.getKeyChar() == ' ') {
                    break;
                }
                text += ke.getKeyChar();
                break;
        }

        return selected;
    }


    @Override
    protected void mousePressed(MouseEvent e) {
        if(!isOn(e.getX(), e.getY()))
            return;

        this.selected = !this.selected;
    }


    @Override
    public void render(Graphics g) {
        int index = (selected) ? 1 : 0;
        g.drawImage(images[index], this.x, this.y, null);
        g.setFont(font);
        g.setColor(c);
        FontMetrics fm = g.getFontMetrics(font);
        int length = fm.stringWidth(text);
        g.drawString(text, (this.x + width / 2) - (length / 2), (this.y + height / 2) + (font.getSize() / 2) - 3);
    }
}
