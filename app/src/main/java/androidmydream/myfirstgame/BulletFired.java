package androidmydream.myfirstgame;

        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Matrix;
        import android.graphics.Rect;

        import java.util.Random;

public class BulletFired extends GameObject{
    private Bitmap top,left,bottom,right;

    private static final int SPEED = 9;
    private boolean playing;
    private long startTime;

    private int HEIGHT;
    private int WIDTH;

    public int x;

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int y;

    private Bitmap imageFinal;
    private int direction;

    Player player;

    public BulletFired(Bitmap top,Bitmap left,Bitmap bottom,Bitmap right, int w, int h, Player player) {
        this.x = (player.getX() + (player.getX()+player.WIDTH))/2;
        this.y = (player.getY()+ (player.getY()+player.HEIGHT))/2;

        this.HEIGHT = h;
        this.WIDTH = w;

        this.player=player;
        this.direction=player.getDirection();
        this.top=getResizedBitmap(top, h, w);
        this.left=getResizedBitmap(left,w,h);
        this.bottom=getResizedBitmap(bottom,h,w);
        this.right=getResizedBitmap(right, w, h);

        startTime = System.nanoTime();

        dy = direction;
        dx = direction;
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

        System.out.println("IN SETUP BULLET");
        switch(direction)
        {
            case 11:
                dy=(-1)*(SPEED);
                dx=0;
                break;
            case 22:
                dx=(-1)*(SPEED);
                dy=0;
                break;
            case 33:
                dy=(+1)*(SPEED);
                dx=0;
                break;
            case 44:
                dx=(+1)*(SPEED);
                dy=0;
                break;
            case 0:
                dy=(-1)*(SPEED);
                dx=0;
                System.out.println("REACHED 0 in SETUP BULLET");
                break;
        }
        System.out.println("BULLET SETUP with DIRECTION :"+direction);
        System.out.println("END SETUP BULLET");
    }
    public void update() {
        System.out.println("IN UPDATE BULLET");
        long elapsed = (System.nanoTime() - startTime) / 1000000;

        if((x > (GamePanel.WIDTH-this.WIDTH)) || (y>(GamePanel.HEIGHT-this.HEIGHT)) || (x<0) || (y<0))
        {
            this.x = (player.getX() + (player.getX()+player.WIDTH))/2;
            this.y = (player.getY()+ (player.getY()+player.HEIGHT))/2;
            this.direction=player.getDirection();
        }
        else
        {
            x+=dx;
            y+=dy;
        }
        System.out.println("UPDATED: BULLET LOCATION - "+x+" : "+y);
    }
    public Rect getRect()
    {
        return new Rect(this.x, this.y,this.WIDTH,this.HEIGHT);
    }
    public void draw(Canvas canvas) {
        System.out.println("REACHED BULLET DRAW");
        System.out.println("BULLET DIRECTION : " + direction);

        if (direction == 11) {
            imageFinal = top;
        } else if (direction == 22) {
            imageFinal = left;
        } else if (direction == 33) {
            imageFinal = bottom;
        } else if (direction == 44) {
            imageFinal = right;
        } else if(direction==0)
            imageFinal=top;
        else
            imageFinal = null;

        if (imageFinal != null || direction==0) {
            canvas.drawBitmap(imageFinal, x, y, null);
        } else {
            canvas.drawBitmap(top, x, y, null);
        }

        System.out.println("BULLET LOCATION:" + x + "::" + y);
    }
}