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

    public static double[] PARABEL_RECHNEN(double x1, double y1, double x2, double y2, double x3, double y3){
        double[] temp = new double[3];
        temp[0] = (x1 * (y2-y3)+ x2 * (y3-y1)+x3 * (y1-y2))/((x1-x2) * (x1-x3) * (x3-x2));
        temp[1] = (Math.pow(x1,2) * (y2-y3)+Math.pow(x2,2) *(y3-y1)+Math.pow(x3,2) *(y1-y2))/((x1-x2)*(x1-x3)*(x2-x3));
        temp[2] = (Math.pow(x1,2) * (x2 * y3-x3 * y2)+ x1 * (+Math.pow(x3,2) * y2-Math.pow(x2,2) *y3)+ x2 * x3 * y1 * (x2-x3))/((x1-x2)*(x1-x3)*(x2-x3));
        return temp;
    }

    public static double GET_RANDOM_ZAHL(double min, double max){
        return (Math.random() * max) + min;
    }

    public static double GET_RANDOM_ZAHL_INC_NEGATIV(double min, double max){
        double random = GET_RANDOM_ZAHL(min, max);
        if(random < random / 2){
            return random;
        }else{
            return -random;
        }
    }
}
