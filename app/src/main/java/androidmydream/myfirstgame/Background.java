package androidmydream.myfirstgame;

        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Matrix;
        import android.graphics.Paint;
        import android.graphics.Rect;

public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res)
    {
        image = getResizedBitmap(res,GamePanel.WIDTH,GamePanel.HEIGHT);
        dx=GamePanel.MOVESPEED;

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
    public void update()
    {
        x+=dx;
        if(x<-GamePanel.WIDTH){
            x=0;
        }
    }
    public void draw(Canvas canvas)
    {

        canvas.drawBitmap(image, 0, 0, null);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawRect(GamePanel.WIDTH / 4, GamePanel.HEIGHT - GamePanel.HEIGHT / 4, (GamePanel.WIDTH / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), (GamePanel.HEIGHT - GamePanel.HEIGHT / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), paint);
        //canvas.drawCircle(GamePanel.WIDTH / 4, GamePanel.HEIGHT - GamePanel.HEIGHT / 4, GamePanel.WIDTH / 16, paint);
        //paint.setColor(Color.YELLOW);
        canvas.drawRect(GamePanel.WIDTH / 4, GamePanel.HEIGHT-GamePanel.HEIGHT/16, (GamePanel.WIDTH / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), (GamePanel.HEIGHT-GamePanel.HEIGHT/16) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), paint);
        //canvas.drawCircle(GamePanel.WIDTH/4,GamePanel.HEIGHT-GamePanel.HEIGHT/16,GamePanel.WIDTH/16,paint);
        //paint.setColor(Color.RED);
        canvas.drawRect(GamePanel.WIDTH / 16, GamePanel.HEIGHT-(5*GamePanel.HEIGHT)/32, (GamePanel.WIDTH / 16) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), (GamePanel.HEIGHT-(5*GamePanel.HEIGHT)/32) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), paint);
        //canvas.drawCircle(GamePanel.WIDTH/16,GamePanel.HEIGHT-(5*GamePanel.HEIGHT)/32,GamePanel.WIDTH/16,paint);
        //paint.setColor(Color.BLUE);
        canvas.drawRect((7*GamePanel.WIDTH)/16, GamePanel.HEIGHT - (5 * GamePanel.HEIGHT) / 32,(7*GamePanel.WIDTH)/16 + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), (GamePanel.HEIGHT - (5 * GamePanel.HEIGHT) / 32) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), paint);
        //canvas.drawCircle((7*GamePanel.WIDTH)/16,GamePanel.HEIGHT-(5*GamePanel.HEIGHT)/32,GamePanel.WIDTH/16,paint);
        System.out.println("REACHED BG");

    }
}
