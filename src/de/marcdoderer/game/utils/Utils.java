package de.marcdoderer.game.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class Utils {

    /**
     * Looks if the path is a valid folder and returns all pictures in it.
     * @param path
     * @return
     * @throws IllegalArgumentException
     * @throws IOException
     */
    public static BufferedImage[] loadPictures(String path) throws IllegalArgumentException, IOException {
        File folder = new File(path);
        if(!folder.isDirectory()){
            throw new IllegalArgumentException("Sprite path is not valid");
        }

        FilenameFilter fnf = (file, s) -> s.endsWith(".png") || s.endsWith(".gif") || s.endsWith(".jpg");


        int length = folder.listFiles(fnf).length;
        BufferedImage[] sprites = new BufferedImage[length];
        for(int i = 0; i < length; i++){
            sprites[i] = (ImageIO.read(folder.listFiles(fnf)[i]));
        }

        return sprites;
    }
}
