package androidmydream.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;

/**
 * Created by Madhuri on 3/3/2016.
 */
public class FireBall extends GameObject
{
    public int HEIGHT, WIDTH;
    public int x,y;

    private static final int MOVESPEED = 10;

    // compass direction/bearing constants
    private static final int NUM_DIRS = 8;
    private static final int N = 0;
    private static final int NE = 1;
    private static final int E = 2;
    private static final int SE = 3;
    private static final int S = 4;
    private static final int SW = 5;
    private static final int W = 6;
    private static final int NW = 7;

    public int direction;

    Point[] incrs;

    Bitmap enemyball;

    public FireBall(Bitmap img, int h, int w, int xloc, int yloc)
    {
        System.out.println("REACHED ENEMY FIREBALL CONSTRUCTOR");
        this.HEIGHT=h;
        this.WIDTH=w;

        this.x=xloc;
        this.y=yloc;

        this.enemyball=img;
        System.out.println("RECREATE THE NEW ENEMY");

        this.direction = (int)( Math.random()*8 );

        // increments for each compass dir
        incrs = new Point[NUM_DIRS];
        incrs[N] = new Point(0, -1);
        incrs[NE] = new Point(-1, -1);
        incrs[E] = new Point(-1, 0);
        incrs[SE] = new Point(-1, 1);
        incrs[S] = new Point(0, 1);
        incrs[SW] = new Point(1, 1);
        incrs[W] = new Point(1, 0);
        incrs[NW] = new Point(1, -1);

        System.out.println("FIRE BALL CONSTRUCTED SUCCESSFULLY");
        //Check wether we use Point2D or Simple point for this above
				/*Point2D provides best location capability */
    }
    public void update(int direction)
    {
        System.out.println("REACHED ENEMY UPDATE with "+direction);
        Point incr = incrs[direction];
        this.x += (incr.x * (MOVESPEED));
        this.y += (incr.y * (MOVESPEED));

        this.direction = direction;
    }
    public void draw(Canvas canvas)
    {
        System.out.println("REACHED ENEMY FIREBALL DRAW");
        canvas.drawBitmap(enemyball,x,y,null);
    }
    public Rect getRect()
    {
        //return new Rect(this.x, this.y,this.WIDTH,this.HEIGHT);
        return new Rect(this.x , this.y ,this.x+enemyball.getWidth(),this.y+enemyball.getHeight());
    }
}
