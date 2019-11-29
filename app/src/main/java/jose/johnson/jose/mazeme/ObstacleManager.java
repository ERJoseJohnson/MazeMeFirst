package jose.johnson.jose.mazeme;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

import static jose.johnson.jose.mazeme.MainThread.canvas;

/**
 * Created by Jose Johnson on 19/1/2018.
 */

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private Player player;
    private Obstacle obstacle;

    private long startTime;
    private long initTime; //Makes the game go faster with time
    private int score = 0;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.color = color;
        this.obstacleHeight = obstacleHeight;

        startTime = initTime =  System.currentTimeMillis();

        obstacles = new ArrayList<>();
        populateObstacles();
    }
    public boolean playerCollide(Player player){
        for(Obstacle ob : obstacles){
            if(ob.playerCollide(player)){
                return true;
            }
        }return false;
    }

    private void populateObstacles(){
        int currY = -5*Constants.SCREEN_HEIGHT/4;       //Makes the obstacles flow from the top

        while (currY<0) {
            int xstart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xstart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update() {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime); //This is so we can manipulate the speed at which the obstacles move
        startTime = System.currentTimeMillis();
        float speed =(float) Math.sqrt(1+(startTime-initTime)/2000)*(Constants.SCREEN_HEIGHT / 10000.0f);
        for (Obstacle ob : obstacles) {
            ob.incrementY(speed * elapsedTime);
        }
        if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xstart = (int) (Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xstart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap, playerGap));
            obstacles.remove(obstacles.size()-1);
            score++;
        }
    }

    public void draw(Canvas canvas){
        for(Obstacle ob : obstacles){
            ob.draw(canvas);
            Paint p = new Paint();
            p.setTextSize(100);
            p.setColor(Color.BLUE);
            canvas.drawText(""+score,50, 100, p);
        }
    }

}

