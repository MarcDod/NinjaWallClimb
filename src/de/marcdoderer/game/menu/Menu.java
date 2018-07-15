package de.marcdoderer.game.menu;

import de.marcdoderer.game.entities.Character;
import de.marcdoderer.game.ingame.Game;
import de.marcdoderer.game.main.Gui;
import de.marcdoderer.game.states.State;
import de.marcdoderer.game.states.StateManager;
import de.marcdoderer.game.utils.Sprite;
import de.marcdoderer.game.utils.Utils;
import de.marcdoderer.json.JSONUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Creates a new Menu state with buttons and a textfield
 */
public class Menu extends State {

    private Textfield textfield;
    private Button[] buttons;

    private Sprite background[];
    private Character[] characters;

    private BufferedImage[] preview;

    private int currentCharacter;


    public Menu(StateManager stm){
        super(stm);

        characters[currentCharacter].getWalkLeft().startSprite();
    }


    /**
     * swap the current character. Stops the old animation and starts the new one.
     * @param direction indicating of left(-1) or right(1) arrow is pressed to choose addon
     */
    private void changeCharacter(int direction){
        characters[currentCharacter].getWalkLeft().stopSprite();
        if (direction == 1) {
            currentCharacter = (currentCharacter < characters.length - 1) ? (currentCharacter += direction) : 0;
        }else{
            currentCharacter = (currentCharacter > 0) ? (currentCharacter += direction) : characters.length - 1;
        }
        characters[currentCharacter].getWalkLeft().startSprite();
    }


    @Override
    public void update() {
    }


    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Gui.SCREEN_WIDTH, Gui.SCREEN_HEIGHT);



        for(int i = 0; i < preview.length; i++) {
            g.drawImage(preview[i], 120, 328 - (i * 200), null);
        }




        for(int i = 0; i < background.length; i++) {
            int x = (i == 0) ? 0 : background[i - 1].getSprite().getWidth();
            g.drawImage(background[i].getSprite(), i * x, 40, null);
        }

        // Character
        g.drawImage(characters[currentCharacter].getWalkLeft().getSprite(), 123, 175, null);

        // AddOn
        if(characters[currentCharacter].getAddonSprite() != null)
            g.drawImage(characters[currentCharacter].getAddonSprite().getSprite(), 165, 360, null);

        textfield.render(g);
        for(Button b : buttons){
            b.render(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        if(textfield.keyPressed(ke)) return;
        switch(ke.getKeyCode()){
            case KeyEvent.VK_RIGHT:
                changeCharacter(1);
                break;
            case KeyEvent.VK_LEFT:
                changeCharacter(-1);
                break;
            case KeyEvent.VK_A:
                characters[currentCharacter].changeAddon(-1);
                break;
            case KeyEvent.VK_D:
                characters[currentCharacter].changeAddon(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        textfield.mousePressed(e);
        for(Button b : buttons){
            b.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(Button b : buttons){
            b.mouseReleased(e);
        }
    }

    @Override
    public void actionPerformed(String ID) {
        switch (ID){
            case "LastCharacter":
                changeCharacter(-1);
                break;
            case "NextCharacter":
                changeCharacter(1);
                break;
            case "LastAddon":
                characters[currentCharacter].changeAddon(-1);
                break;
            case "NextAddon":
                characters[currentCharacter].changeAddon(1);
                break;
            case "Quit":
                System.exit(0);
                break;
            case "Play":
                this.cleanUp();
                stm.pushState(new Game(stm, characters[currentCharacter]));
                break;
            default:
                break;
        }
    }

    @Override
    public void cleanUp() {
        for(Sprite s : background){
            s.stopSprite();
        }
        characters[currentCharacter].getWalkLeft().stopSprite();

        if(characters[currentCharacter].getAddonSprite() == null) return;

        characters[currentCharacter].getAddonSprite().stopSprite();
    }

    @Override
    public void ressume() {
        for(Sprite s : background){
            s.startSprite();
        }
        characters[currentCharacter].getWalkLeft().startSprite();

        if(characters[currentCharacter].getAddonSprite() == null) return;

        characters[currentCharacter].getAddonSprite().startSprite();
    }

    @Override
    public void init() {
        this.background = new Sprite[2];
        this.buttons = new Button[6];
        this.currentCharacter = 0;

        try {
            preview = Utils.loadPictures("rsc/menü/cityTheme/background/preview");

            textfield = new Textfield(320, 5, "rsc/menü/cityTheme/textfield/username", "UserName", new Font("TimesRoman", Font.PLAIN, 20), Color.BLACK);

            // Character swap buttons
            buttons[0] = new Button(70, 170, "rsc/menü/cityTheme/buttons/arrowLeft", "LastCharacter", this);
            buttons[1] = new Button(70 + preview[1].getWidth() + 55, 170, "rsc/menü/cityTheme/buttons/arrowRight", "NextCharacter", this);

            // Addon swap buttons
            buttons[2] = new Button(70, 370, "rsc/menü/cityTheme/buttons/arrowLeft", "LastAddon", this);
            buttons[3] = new Button(70 + preview[1].getWidth() + 55, 370, "rsc/menü/cityTheme/buttons/arrowRight", "NextAddon", this);

            // Play/Quit buttons
            buttons[4] = new Button(Gui.SCREEN_WIDTH / 2 - 75, 200, "rsc/menü/cityTheme/buttons/play", "Play", this);
            buttons[5] = new Button(Gui.SCREEN_WIDTH / 2 - 75, 340, "rsc/menü/cityTheme/buttons/quit", "Quit", this);

            background[0] = new Sprite("rsc/menü/cityTheme/background/backgroundLeft", 140,0);
            background[1] = new Sprite("rsc/menü/cityTheme/background/backgroundRight", 140,0);


            for(Sprite s: background){
                s.startSprite();
            }

            File f = new File("assets");

            if(!f.exists()) throw new IllegalArgumentException("Folder not found");

            FilenameFilter fnf = (file, s) -> s.endsWith(".json");

            int length = f.listFiles(fnf).length;

            characters = new Character[length];
            for(int j = 0; j < length; j++){
                JSONObject character = JSONUtils.getJSONObjectFromFile(f.listFiles(fnf)[j]);
                JSONArray addons = character.getJSONArray("AddOns");
                String[] addonStrings = new String[addons.length()+1];
                addonStrings[0] = "";
                for(int i = 0; i < addons.length(); i++){
                    addonStrings[i + 1] = addons.getString(i);
                }
                characters[j] = new Character(character.getString("name"), addonStrings,
                        character.getInt("delay"), character.getInt("jumpDelay"),
                        character.getInt("walkOffset"),character.getInt("jumpOffset"),0);
            }


        } catch (IOException | IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
