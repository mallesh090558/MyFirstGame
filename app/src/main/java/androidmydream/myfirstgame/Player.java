package androidmydream.myfirstgame;

        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Matrix;

        import java.util.Random;

public class Player extends GameObject{
    private Bitmap top,left,bottom,right;
    private int score;

    private static final int SPEED = 5;
    private boolean playing;
    private long startTime;

    private int HEIGHT;
    private int WIDTH;

    private Bitmap imageFinal;
    private int direction;

    //public Player(Bitmap res, int w, int h, int numFrames) {
    public Player(Bitmap top,Bitmap left,Bitmap bottom,Bitmap right, int w, int h, int numFrames) {
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT/2;
        dy = 0;
        dx = 0;
        score = 0;
        this.HEIGHT = h;
        this.WIDTH = w;

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
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 100) {
            score++;
            startTime = System.nanoTime();
        }
        if(x > (GamePanel.WIDTH-this.WIDTH))
            x=GamePanel.WIDTH-this.WIDTH;
        else if(y>(GamePanel.HEIGHT-this.HEIGHT))
            y=GamePanel.HEIGHT-this.HEIGHT;
        else if(x<0)
            x=0;
        else if(y<0)
            y=0;
        else
        {
            x+=dx;
            y+=dy;
        }
        System.out.println("UPDATED: LOCATION - "+x+" : "+y);
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
            canvas.drawBitmap(imageFinal, x, y, null);
        }
        else
        {
            canvas.drawBitmap(top, x, y, null);
        }

        System.out.println("LOCATION:"+x+"::"+y);
    }
    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDY(){dy = 0;}
    public void resetScore(){score = 0;}
}