package de.marcdoderer.game.ingame.obstacle;

import de.marcdoderer.game.entities.ninja.Ninja;

import java.awt.*;
import java.util.ArrayList;

public class ObstacleManager {

    private static ArrayList<Obstacle[]> obstacles;
    private boolean pause;

    public ObstacleManager(){
        init();
    }

    public static void addNewObstacle(Obstacle obstacle, int number){
        Obstacle tempObstackle[] = new Obstacle[number];
        ObstacleManager.obstacles.add(tempObstackle);
        for(int i =0; i  < number; i++){
            ObstacleManager.obstacles.get(ObstacleManager.obstacles.indexOf(tempObstackle))[i] = obstacle.getInstanceOf();
        }
    }


    public static void setObstaclesSpeed(int fallSpeed){
        for(Obstacle[] obstacles : ObstacleManager.obstacles){
            for(Obstacle o : obstacles){
                o.setFallSpeed(fallSpeed);
            }
        }
    }

    public void update(){
        if(pause) return;
        for(Obstacle[] obstacles : ObstacleManager.obstacles){
            obstacles[0].setSpawnTime(obstacles[0].getTimeSinceLastSpawn() + 1);
            for(Obstacle o : obstacles){
                if(o.getIsAlive()){
                    o.update();
                }else{
                    //System.out.println(obstacles[i].getTimeSinceLastSpawn());
                    if(o.getTimeSinceLastSpawn() < o.getSpawnDistance()) continue;
                    if((float) (Math.random()) > o.getSpawnProbability()) continue;
                    o.startObstacle();
                }
            }
        }
    }

    public boolean lookForHit(Ninja ninja){
        boolean hit = false;
        for(Obstacle[] obstacles : ObstacleManager.obstacles){
            for(Obstacle o : obstacles){
                if(o.getIsAlive() && !pause){
                    hit = o.hit(ninja);
                }
            }
        }
        return hit;
    }

    public void render(Graphics g){
        for(Obstacle[] obstacles : ObstacleManager.obstacles){
            for(Obstacle o : obstacles){
                if(o.getIsAlive()){
                    o.render(g);
                }
            }
        }
    }

    public void pause(){
        this.pause = true;
        for(Obstacle[] obstacles : ObstacleManager.obstacles){
            for(Obstacle o : obstacles){
                if(o.getIsAlive()){
                    o.pauseSprite();
                }
            }
        }
    }

    public void resume(){
        this.pause = false;
        for(Obstacle[] obstacles : ObstacleManager.obstacles){
            for(Obstacle o : obstacles){
                if(o.getIsAlive()){
                    o.returnSprite();
                }
            }
        }
    }

    public void cleanUp(){
        for(Obstacle[] obstacles : ObstacleManager.obstacles){
            for(Obstacle o : obstacles){
                o.reset();
            }
        }
    }

    private void init(){
        ObstacleManager.obstacles = new ArrayList<>();;
        this.pause = false;
    }
}
