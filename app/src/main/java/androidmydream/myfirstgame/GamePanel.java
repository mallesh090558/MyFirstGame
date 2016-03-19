package androidmydream.myfirstgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    public static int WIDTH;
    public static int HEIGHT;
    public static final int MOVESPEED = 0;
    public static final int ENEMYGENSPEED = 5;
    private MainThread thread;
    private Background bg;
    private DirectionPanel dp;
    private Player player;
    private BulletFired bullet_fired;
    private EnemyGroup enemygroup;

    private Bitmap bullet_top,bullet_bottom,bullet_right,bullet_left;
    private long endinProcessCountTime;

    public static boolean isGameOver=false;
    public static boolean isRestarted=false;
    public static boolean ispaused=false;
    public static boolean RestarttheGame=false;
    public static int score,level;

    final GestureDetector mGesDetect;

    public GamePanel(Context context)
    {
        super(context);
        GamePanel.score=0;
        DisplayMetrics dm = new DisplayMetrics();
        ((MainActivity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);

        mGesDetect = new GestureDetector(this.getContext(), new DoubleTapGestureDetector());

        WIDTH  = dm.widthPixels;
        HEIGHT = dm.heightPixels;

        System.out.println("PANEL WIDTH:"+ WIDTH+"\tHEIGHT"+ HEIGHT);

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

        Bitmap play = BitmapFactory.decodeResource(getResources(),R.drawable.play_new);
        Bitmap pause = BitmapFactory.decodeResource(getResources(),R.drawable.paused);
        Bitmap gameover = BitmapFactory.decodeResource(getResources(),R.drawable.gameover);

        Bitmap top_dir = BitmapFactory.decodeResource(getResources(),R.drawable.top);
        Bitmap down_dir = BitmapFactory.decodeResource(getResources(),R.drawable.down);
        Bitmap right_dir = BitmapFactory.decodeResource(getResources(),R.drawable.right);
        Bitmap left_dir = BitmapFactory.decodeResource(getResources(),R.drawable.left);

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.space_bg),play,pause,top_dir,down_dir,right_dir,left_dir,gameover);
        Bitmap tank_top = BitmapFactory.decodeResource(getResources(),R.drawable.top_robot);
        Bitmap tank_bottom = BitmapFactory.decodeResource(getResources(),R.drawable.down_robot);
        Bitmap tank_right = BitmapFactory.decodeResource(getResources(),R.drawable.right_robot);
        Bitmap tank_left = BitmapFactory.decodeResource(getResources(),R.drawable.left_robot);

        //DIRECTION BUTTONS LOADING


        //player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.tank), 65, 25, 1);

        player = new Player(tank_top,tank_left,tank_bottom,tank_right, GamePanel.HEIGHT/8, GamePanel.WIDTH/6, 1);
        dp=new DirectionPanel();

        bullet_top = BitmapFactory.decodeResource(getResources(),R.drawable.fire_bullet);
        bullet_bottom = BitmapFactory.decodeResource(getResources(),R.drawable.fire_bullet);
        bullet_right = BitmapFactory.decodeResource(getResources(),R.drawable.fire_bullet);
        bullet_left = BitmapFactory.decodeResource(getResources(),R.drawable.fire_bullet);

        bullet_fired = new BulletFired(bullet_top, bullet_bottom, bullet_right, bullet_left, 25, 25, player);

        endinProcessCountTime = 0;
        level=10;

        enemygroup = new EnemyGroup(BitmapFactory.decodeResource(getResources(), R.drawable.fire_bullet), bullet_fired, player);
        enemygroup.addEnemy();

        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    public void restartTheGame()
    {
        GamePanel.isRestarted=true;
        endinProcessCountTime = 0;
        thread.setRunning(true);
        GamePanel.isGameOver=false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        System.out.println("IN TOUCH EVENT");
        mGesDetect.onTouchEvent(event);
        if(GamePanel.isGameOver && GamePanel.RestarttheGame)
        {
            GamePanel.RestarttheGame=false;
            restartTheGame();
        }
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
            else if(direction==55)
            {
                if(GamePanel.ispaused==false)
                {
                    GamePanel.ispaused = true;
                    thread.pause();
                }
                else
                {
                    GamePanel.ispaused=false;
                    thread.unpause();
                }
            }
            else
            {
                player.setUp(direction);
                bullet_fired.setUp(direction);
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
        if(!isGameOver)
        {
            if(player.getPlaying())
            {
                bg.update();
                player.update();
                bullet_fired.update();

                if(bullet_fired==null)
                bullet_fired = new BulletFired(bullet_top, bullet_bottom, bullet_right, bullet_left, GamePanel.WIDTH/10, GamePanel.WIDTH/10, player);
            }
        }
    }
    @Override
    public void draw(Canvas canvas)
    {
        final float scaleFactorX = getWidth()/(WIDTH*1.f);
        final float scaleFactorY = getHeight()/(HEIGHT*1.f);

        if(canvas!=null)
        {
            final int savedState = canvas.save();

            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            System.out.println("REACHED");
            if(!isGameOver && !GamePanel.ispaused) {
                player.draw(canvas);
                bullet_fired.draw(canvas);

                if (endinProcessCountTime == 0) {
                    endinProcessCountTime = (ENEMYGENSPEED * 1000) + System.currentTimeMillis();
                }

                System.out.println("ENEMY TIME CREATION : " + endinProcessCountTime + " DIFFERENCE :" + System.currentTimeMillis() + " SCORE : " + GamePanel.score);
                if (System.currentTimeMillis() > endinProcessCountTime) {

                    if (GamePanel.score > level)
                        level += 10;
                    System.out.println("ENTERED to CREATE ENEMY");
                    for (int i = 0; i < (level / 10); i++) {
                        enemygroup.addEnemy();
                    }
                    endinProcessCountTime = 0;
                }

                enemygroup.EnemyDraw(canvas);
                canvas.restoreToCount(savedState);
            }
            if(GamePanel.isRestarted==true)
            {
                GamePanel.isRestarted=false;
                GamePanel.score=0;
                GamePanel.level=0;
            }
        }
    }
}