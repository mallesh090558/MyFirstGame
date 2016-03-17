package androidmydream.myfirstgame;

import android.graphics.Rect;

/**
 * Created by Madhuri on 2/23/2016.
 */
public class DirectionPanel
{
    private int upX, downX, rightX, leftX;
    private int upY, downY, rightY, leftY;
    private int radius;

    Rect up,down,right,left,halt,start;

    DirectionPanel()
    {
        upX      = GamePanel.WIDTH / 4;
        upY      = GamePanel.HEIGHT - GamePanel.HEIGHT / 4;
        downX    = GamePanel.WIDTH / 4;
        downY    = GamePanel.HEIGHT - GamePanel.HEIGHT / 16;
        rightX   = GamePanel.WIDTH/16;
        rightY   = GamePanel.HEIGHT-(5*GamePanel.HEIGHT)/32;
        leftX    = (7*GamePanel.WIDTH)/16;
        leftY    = GamePanel.HEIGHT-(5*GamePanel.HEIGHT)/32;
        radius	 = GamePanel.WIDTH / 16;

        up = new Rect(upX,upY,(upX)+(radius*(2^(1/20))),(upY)+(radius*(2^(1/20))));
        down = new Rect(downX,downY,(downX)+(radius*(2^(1/2))),(downY)+(radius*(2^(1/2))));
        right = new Rect(rightX,rightY,(rightX)+(radius*(2^(1/2))),(rightY)+(radius*(2^(1/2))));
        left = new Rect(leftX,leftY,(leftX)+(radius*(2^(1/2))),(leftY)+(radius*(2^(1/2))));

        halt = new Rect(0,0,50,50);
        start = new Rect((GamePanel.WIDTH/2)-(250/2),(GamePanel.HEIGHT/2)-(250/2),(GamePanel.WIDTH/2)-(250/2)+250,(GamePanel.HEIGHT/2)-(250/2)+250);
    }
/*
    public int isPointInsideCircle(int x, int y)
    {
        if((((x - upX)^2 + (y - upY)^2) - (radius^2)) <= 0 && (((x - upX)^2 + (y - upY)^2) - (radius^2)) > (-1*radius)) {
            System.out.println("CALUCULATED UP DIRECTION:"+(((x - upX)^2 + (y - upY)^2)-radius^2));
            return 11;
        }
        if(((x - leftX)^2 + (y - leftX)^2) - (radius^2) <= 0 && (((x - leftX)^2 + (y - leftX)^2) - (radius^2)) > (-1*radius)) {
            System.out.println("CALUCULATED LEFT DIRECTION:"+(((x - leftX)^2 + (y - leftY)^2)-radius^2));
            return 44;
        }
        if(((x - rightX)^2 + (y - rightY)^2) - (radius^2) <= 0 && (((x - rightX)^2 + (y - rightX)^2) - (radius^2)) > (-1*radius)) {
            System.out.println("CALUCULATED RIGHT DIRECTION:"+(((x - rightX)^2 + (y - rightY)^2)-radius^2));
            return 22;
        }
        if(((x - downX)^2 + (y - downY)^2) - (radius^2) <= 0 && (((x - downX)^2 + (y - downX)^2) - (radius^2)) > (-1*radius)) {
            System.out.println("CALUCULATED DOWN DIRECTION:"+(((x - downX)^2 + (y - downY)^2)-radius^2));
            return 33;
        }
        return 0;
    }
    */
    public int isPointInsideClicked(int x, int y)
    {
        if(up.contains(x,y))
            return 11;
        else if(right.contains(x,y))
            return 22;
        else if(down.contains(x,y))
            return 33;
        else if(left.contains(x,y))
            return 44;
        else if(halt.contains(x,y))
        {
            return 55;
        }
        else if(start.contains(x,y))
        {
            return 55;
        }
        else
        return 0;
    }
}
