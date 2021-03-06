package androidmydream.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Madhuri on 3/3/2016.
 */
public class EnemyGroup
{
    private ArrayList<FireBall> obj;

    private int direction;
    private Bitmap img;

    private BulletFired bulletFired;
    private Player player;

    EnemyGroup(Bitmap img, BulletFired bulletFired, Player player)
    {
        obj = new ArrayList<FireBall>();
        this.img=img;

        this.bulletFired=bulletFired;
        this.player=player;
    }

    public void addEnemy()
    {
        System.out.println("REACHED ENEMY GROUP");
        FireBall f = new FireBall(img ,10,10,GamePanel.WIDTH/4, GamePanel.HEIGHT/4);
        obj.add(f);
        System.out.println("ENEMY CREATED SUCCESSFULLY ! COUNT - "+obj.size());
    }

    public void collisionCheck(int i)
    {
            FireBall f = obj.get(i);
            int h = f.HEIGHT;
            int w = f.WIDTH;
            int xloc = f.x;
            int yloc = f.y;

            if(xloc < 0)
            {
                // Check wether it represents 4 Directions or Five

                int[] num={7,6,5};
                direction = getRandomDirection(num);
                System.out.println("DIRECTION OF ENEMY 1: "+direction);
                f.update(direction);
            }
            else if(xloc > GamePanel.WIDTH-100)
            {
                // Check wether it represents 4 Directions or Five
                int[] num={1,2,3};
                direction = getRandomDirection(num);

                System.out.println("DIRECTION OF ENEMY 2: "+direction);
                f.update(direction);
            }
            else if(yloc < 0)
            {
                int[] num={3,4,5};
                direction = getRandomDirection(num);

                System.out.println("DIRECTION OF ENEMY 3: "+direction);
                f.update(direction);
            }
            else if(yloc > GamePanel.HEIGHT-100)
            {
                int[] num={0,1,7};
                direction = getRandomDirection(num);

                System.out.println("DIRECTION OF ENEMY 4: "+direction);
                f.update(direction);
            }
            else
            {
                //Rect r2=new Rect(f.x,f.y,f.WIDTH,f.HEIGHT);
                int noupdate=1;
                for(int j=0;j<obj.size();j++)
                {
                    FireBall other= obj.get(j);
                    /*Rect r1=new Rect(other.x,other.y,other.WIDTH,other.HEIGHT);

                    System.out.println("DIRECTION OF ENEMY 5 REACHED RECTANGLE R AT "+j+" WITH "+r1.centerX()+":"+r1.centerY()+":"+r1.width()+":"+r1.height()+" ALONG "+Rect.intersects(r1, r2));
                    System.out.println("DIRECTION OF ENEMY 5 REACHED RECTANGLE D AT "+j+" WITH "+other.x+":"+f.x+":"+other.y+":"+f.y+" ALONG "+r1.intersect(r2));
                    */
                    if(i!=j)
                    {
                        if (rectangle_collision(f.x, f.y, f.WIDTH, f.HEIGHT, other.x, other.y, other.WIDTH, other.HEIGHT))
                        {
                            int[] num = {0, 1, 2, 3, 4, 5, 6, 7};
                            direction = getRandomDirection(num);

                            System.out.println("DIRECTION OF ENEMY 5 SUCCESS");
                            f.update(direction);
                            noupdate = 0;

                        }
                    }
                    /*
                    if( (r1.intersect(r2)) && (i!=j))
                    {
                        int[] num={0,1,2,3,4,5,6,7};
                        direction = getRandomDirection(num);

                        System.out.println("DIRECTION OF ENEMY 5 SUCCESS");
                        f.update(direction);
                        noupdate=0;
                    }
                    */
                }
                if(noupdate==1)
                {
                    System.out.println("DIRECTION OF ENEMY 6: " + direction);
                    f.update(f.direction);
                }
            }

    }

    boolean rectangle_collision(float x_1, float y_1, float width_1, float height_1, float x_2, float y_2, float width_2, float height_2)
    {
        return !(x_1 > x_2+width_2 || x_1+width_1 < x_2 || y_1 > y_2+height_2 || y_1+height_1 < y_2);
    }

    public void EnemyDraw(Canvas canvas)
    {
        System.out.println("REACHED ENEMY DRAW ");

        if(GamePanel.isRestarted)
        {
            obj.clear();
            obj=null;
            obj = new ArrayList<FireBall>();
            addEnemy();
        }
        for (int i=0;i< obj.size();i++)
        {
            FireBall f = obj.get(i);
            f.draw(canvas);
            System.out.println("OBJECT IMAGE DRAWN SUCCESSFULLY");
            collisionCheck(i);

            /*
            Paint mypaint= new Paint();
            System.out.println("COLLISION : STATUS in ENEMY GROUP - " + Rect.intersects(f.getRect(), bulletFired.getRect()) + "  & " + Rect.intersects(bulletFired.getRect(), f.getRect()));

            mypaint.setColor(Color.RED);
            canvas.drawRect(f.getRect(), mypaint);

            mypaint.setColor(Color.YELLOW);
            canvas.drawRect(bulletFired.getRect(), mypaint);

            mypaint.setColor(Color.BLUE);
            System.out.println("COLLISION PLAYER VALUES :" + player.getX() + " & " + player.getY() + " :: " + player.WIDTH + " & " + player.HEIGHT);

            canvas.drawRect(player.getRect(), mypaint);
            */
            if(Rect.intersects(f.getRect(), bulletFired.getRect()))
            {
                obj.remove(i);
                GamePanel.score++;
            }
            if(Rect.intersects(f.getRect(), player.getRect()))
            {
                GamePanel.isGameOver=true;
            }

            /*
            if(rectangle_collision(bulletFired.getX(),bulletFired.getY(),bulletFired.getWIDTH(),bulletFired.getHEIGHT(),f.x,f.y,f.WIDTH,f.HEIGHT))
            {
                obj.remove(i);
            }
            if(rectangle_collision(player.getX(),player.getY(),player.WIDTH,player.HEIGHT,f.x,f.y,f.WIDTH,f.HEIGHT))
            {
                System.out.println("GAME OVER");

            }
            */
        }
    }
    public int getRandomDirection(int[] num)
    {
        int number=0;
        Random r = new Random();
        number= r.nextInt(num.length-1);

        System.out.println("ENEMY DIRECTION RETURNED :"+number+" & GUT :"+num[number]);
        return num[number];
    }
}
