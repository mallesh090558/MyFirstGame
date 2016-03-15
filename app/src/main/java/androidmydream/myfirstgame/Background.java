package androidmydream.myfirstgame;

        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Matrix;
        import android.graphics.Paint;
        import android.graphics.Rect;
        import android.graphics.Typeface;

public class Background {

    private Bitmap image,pause,play,gameover;
    private Bitmap top_dir,left_dir,down_dir,right_dir;
    private int x, y, dx;

    public Background(Bitmap res, Bitmap play, Bitmap pause, Bitmap top_dir,Bitmap down_dir, Bitmap right_dir, Bitmap left_dir, Bitmap gameover)
    {
        image = getResizedBitmap(res,GamePanel.WIDTH,GamePanel.HEIGHT);
        dx=GamePanel.MOVESPEED;

        this.play=getResizedBitmap(play,250,250);
        this.pause=getResizedBitmap(pause,50,50);
        this.gameover=getResizedBitmap(gameover,GamePanel.WIDTH,gameover.getHeight());

        this.top_dir=getResizedBitmap(top_dir,(GamePanel.HEIGHT - GamePanel.HEIGHT / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))),(GamePanel.HEIGHT - GamePanel.HEIGHT / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))));
        this.down_dir=getResizedBitmap(down_dir,(GamePanel.HEIGHT - GamePanel.HEIGHT / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))),(GamePanel.HEIGHT - GamePanel.HEIGHT / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))));
        this.right_dir=getResizedBitmap(right_dir,(GamePanel.HEIGHT - GamePanel.HEIGHT / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))),(GamePanel.HEIGHT - GamePanel.HEIGHT / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))));
        this.left_dir=getResizedBitmap(left_dir,(GamePanel.HEIGHT - GamePanel.HEIGHT / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))),(GamePanel.HEIGHT - GamePanel.HEIGHT / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))));
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
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
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
        Paint paint = new Paint();
        if(GamePanel.isGameOver)
        {
            paint.setColor(Color.BLACK);
            canvas.drawRect(new Rect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT), paint);
            canvas.drawBitmap(gameover,0,0,paint);

            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.RED);
            paint.setTextSize(25f);
            Typeface currentTypeFace =  paint.getTypeface();
            Typeface bold = Typeface.create(currentTypeFace, Typeface.BOLD);
            paint.setTypeface(bold);
            canvas.drawText("YOUR SCORE IS "+GamePanel.score,GamePanel.WIDTH/16,GamePanel.HEIGHT-GamePanel.HEIGHT/16,paint);
        }
        else
        {
            canvas.drawBitmap(image, 0, 0, null);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.WHITE);
            canvas.drawRect(GamePanel.WIDTH / 4, GamePanel.HEIGHT - GamePanel.HEIGHT / 4, (GamePanel.WIDTH / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), (GamePanel.HEIGHT - GamePanel.HEIGHT / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), paint);
            //canvas.drawBitmap(top_dir,GamePanel.WIDTH / 4,GamePanel.HEIGHT - GamePanel.HEIGHT / 4,paint);
            //canvas.drawCircle(GamePanel.WIDTH / 4, GamePanel.HEIGHT - GamePanel.HEIGHT / 4, GamePanel.WIDTH / 16, paint);
            //paint.setColor(Color.YELLOW);
            canvas.drawRect(GamePanel.WIDTH / 4, GamePanel.HEIGHT - GamePanel.HEIGHT / 16, (GamePanel.WIDTH / 4) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), (GamePanel.HEIGHT - GamePanel.HEIGHT / 16) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), paint);
            //canvas.drawBitmap(left_dir, GamePanel.WIDTH / 4, GamePanel.HEIGHT - GamePanel.HEIGHT / 16, paint);
            //canvas.drawCircle(GamePanel.WIDTH/4,GamePanel.HEIGHT-GamePanel.HEIGHT/16,GamePanel.WIDTH/16,paint);
            //paint.setColor(Color.RED);
            canvas.drawRect(GamePanel.WIDTH / 16, GamePanel.HEIGHT - (5 * GamePanel.HEIGHT) / 32, (GamePanel.WIDTH / 16) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), (GamePanel.HEIGHT - (5 * GamePanel.HEIGHT) / 32) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), paint);
            //canvas.drawBitmap(down_dir, GamePanel.WIDTH / 16, GamePanel.HEIGHT - (5 * GamePanel.HEIGHT) / 32, paint);
            //canvas.drawCircle(GamePanel.WIDTH/16,GamePanel.HEIGHT-(5*GamePanel.HEIGHT)/32,GamePanel.WIDTH/16,paint);
            //paint.setColor(Color.BLUE);
            canvas.drawRect((7 * GamePanel.WIDTH) / 16, GamePanel.HEIGHT - (5 * GamePanel.HEIGHT) / 32, (7 * GamePanel.WIDTH) / 16 + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), (GamePanel.HEIGHT - (5 * GamePanel.HEIGHT) / 32) + ((GamePanel.WIDTH / 16) * (2 ^ (1 / 2))), paint);
            //canvas.drawBitmap(right_dir,( 7 * GamePanel.WIDTH) / 16, GamePanel.HEIGHT - (5 * GamePanel.HEIGHT) / 32, paint);
            //canvas.drawCircle((7*GamePanel.WIDTH)/16,GamePanel.HEIGHT-(5*GamePanel.HEIGHT)/32,GamePanel.WIDTH/16,paint);
            System.out.println("REACHED BG");

            Bitmap imageicon = pause;
            int halt_x=0;
            int halt_y=0;
            if (GamePanel.ispaused == true) {
                imageicon = play;
                halt_x=(GamePanel.WIDTH-(imageicon.getWidth()/2));
                halt_y=(GamePanel.HEIGHT-(imageicon.getHeight()/2));
            }
            canvas.drawBitmap(imageicon, halt_x, halt_y, paint);
        }
    }
}
