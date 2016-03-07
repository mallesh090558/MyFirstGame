package androidmydream.myfirstgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static int WIDTH;
    public static int HEIGHT;
    public static final int MOVESPEED = 0;
    public static final int ENEMYGENSPEED = 15;
    private long smokeStartTime;
    private MainThread thread;
    private Background bg;
    private DirectionPanel dp;
    private Player player;
    private EnemyGroup enemygroup;

    public static final int UP=11;
    public static final int RIGHT=22;
    public static final int DOWN=33;
    public static final int LEFT=44;

    private long endinProcessCountTime;

    public GamePanel(Context context)
    {
        super(context);
        DisplayMetrics dm = new DisplayMetrics();
        ((MainActivity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        this.WIDTH  = dm.widthPixels;
        this.HEIGHT = dm.heightPixels;

        System.out.println("PANEL WIDTH:"+this.WIDTH+"\tHEIGHT"+this.HEIGHT);

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        int counter = 0;
        while(retry && counter<1000)
        {
            counter++;
            try{thread.setRunning(false);
                thread.join();
                retry = false;

            }catch(InterruptedException e){e.printStackTrace();}

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.ground));
        Bitmap tank_top = BitmapFactory.decodeResource(getResources(),R.drawable.tank_top);
        Bitmap tank_bottom = BitmapFactory.decodeResource(getResources(),R.drawable.tank_bottom);
        Bitmap tank_right = BitmapFactory.decodeResource(getResources(),R.drawable.tank_right);
        Bitmap tank_left = BitmapFactory.decodeResource(getResources(),R.drawable.tank_left);

        //player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.tank), 65, 25, 1);

        player = new Player(tank_top,tank_left,tank_bottom,tank_right, 75, 35, 1);
        dp=new DirectionPanel();

        endinProcessCountTime = 0;

        enemygroup = new EnemyGroup(BitmapFactory.decodeResource(getResources(), R.drawable.fireball));
        enemygroup.addEnemy();

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        System.out.println("IN TOUCH EVENT");

        float locX = event.getX();
        float locY = event.getY();

        int direction=dp.isPointInsideClicked((int) locX, (int) locY);
        System.out.println("TOUCH LOCATION :"+ locX+":"+locY+"-->"+direction);

        if(direction!=0 && (event.getAction()== MotionEvent.ACTION_DOWN))
        {
            if(!player.getPlaying())
            {
                player.setPlaying(true);
            }
            else
            {
                player.setUp(direction);
            }
            return true;
        }
        else if(event.getAction()==MotionEvent.ACTION_UP)
        {
            player.setUp(00);
            return true;
        }
/* SAMPLE LOGIC WORKED START******************************************
        if(locX > GamePanel.WIDTH/2)
        {
            if(!player.getPlaying())
            {
                player.setPlaying(true);
            }
            else
            {
                player.setUp(true);
            }
            return true;
        }
        else if(locX < GamePanel.WIDTH/2)
        {
            player.setUp(false);
            return true;
        }
* SAMPLE LOGIC WORKED END******************************************/

        return super.onTouchEvent(event);
    }

    public void update()
    {
        if(player.getPlaying()) {

            bg.update();
            player.update();
        }
    }
    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null) {
            final int savedState = canvas.save();

            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            System.out.println("REACHED");
            player.draw(canvas);


            if(endinProcessCountTime==0)
            {
                endinProcessCountTime = (ENEMYGENSPEED*1000) + System.currentTimeMillis();
            }

            System.out.println("ENEMY TIME CREATION : " + endinProcessCountTime + " DIFFERENCE :" + System.currentTimeMillis());
            if( System.currentTimeMillis() > endinProcessCountTime )
            {
                enemygroup.addEnemy();
                endinProcessCountTime=0;
            }

            enemygroup.EnemyDraw(canvas);
            canvas.restoreToCount(savedState);
        }
    }


}