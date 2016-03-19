package androidmydream.myfirstgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

public class Player extends GameObject{
    private Bitmap top,left,bottom,right;
    private int score;
    private int x;

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    private int y;

    private static final int SPEED = 5;
    private boolean playing;
    private long startTime;

    public int HEIGHT;
    public int WIDTH;

    private Bitmap imageFinal;

    public int getDirection() {
        return direction;
    }

    private int direction;

    //public Player(Bitmap res, int w, int h, int numFrames) {
    public Player(Bitmap top,Bitmap left,Bitmap bottom,Bitmap right, int w, int h, int numFrames) {
        this.x = GamePanel.WIDTH/2;
        this.y = GamePanel.HEIGHT/2;
        dy = 0;
        dx = 0;
        score = 0;
        this.HEIGHT = h;
        this.WIDTH = w;
        this.direction=0;
        this.top=getResizedBitmap(top,h,w);
        this.left=getResizedBitmap(left,w,h);
        this.bottom=getResizedBitmap(bottom,h,w);
        this.right=getResizedBitmap(right,w,h);

        startTime = System.nanoTime();
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    public void setUp(int direction)
    {
        if(direction!=0)
        this.direction=direction;

        System.out.println("IN SETUP PLAYER");
        switch(direction)
        {
            case 11: dy=(-1)*(SPEED);
                break;
            case 22: dx=(-1)*(SPEED);
                break;
            case 33: dy=(+1)*(SPEED);
                break;
            case 44: dx=(+1)*(SPEED);
                break;
            case 0:
                dx=0;
                dy=0;
                System.out.println("REACHED 0 in SETUP PLAYER");
                break;
        }
        System.out.println("END SETUP PLAYER");
    }
/* SAMPLE LOGIC WORKED START******************************************
    public void setUp(boolean b)
    {
        up = b;
        if(up)
        {
            dy =-1;
        }
        else
        {
            dy =+1;
        }
    }
/* SAMPLE LOGIC WORKED END******************************************/
    public void update() {
        System.out.println("IN UPDATE PLAYER");
        if(GamePanel.isRestarted==true)
        {
            this.x = GamePanel.WIDTH/2;
            this.y = GamePanel.HEIGHT/2;
            dy = 0;
            dx = 0;
            score = 0;

        }
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }
        if(x > (GamePanel.WIDTH-this.WIDTH))
            this.x=GamePanel.WIDTH-this.WIDTH;
        else if(y>(GamePanel.HEIGHT-this.HEIGHT))
            this.y=GamePanel.HEIGHT-this.HEIGHT;
        else if(x<0)
            this.x=0;
        else if(y<0)
            this.y=0;
        else
        {
            this.x+=dx;
            this.y+=dy;
        }
        System.out.println("UPDATED: LOCATION - " + x + " : " + y);
    }

    public void draw(Canvas canvas)
    {
        System.out.println("REACHED PLAYER");
        //canvas.drawBitmap(spritesheet,x,y,null);
        System.out.println("DIRECTION : "+direction);

        if(direction==11)
        {
            imageFinal=top;
        }
        else if(direction==22)
        {
            imageFinal=left;
        }
        else if(direction==33)
        {
            imageFinal=bottom;
        }
        else if(direction==44)
        {
            imageFinal=right;
        }
        else
            imageFinal=null;

        if(imageFinal!= null) {
            canvas.drawBitmap(imageFinal, this.x, this.y, null);
        }
        else
        {
            canvas.drawBitmap(top, this.x, this.y, null);
        }
        this.HEIGHT=imageFinal.getHeight();
        this.WIDTH=imageFinal.getWidth();
        System.out.println("LOCATION:"+x+"::"+y);
    }
    public Rect getRect()
    {
        return new Rect(this.getX(), this.getY(),this.getX()+imageFinal.getWidth(),this.getY()+imageFinal.getHeight());
    }
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}

}