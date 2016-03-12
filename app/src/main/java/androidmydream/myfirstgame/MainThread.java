package androidmydream.myfirstgame;

        import android.graphics.Canvas;
        import android.view.SurfaceHolder;

public class MainThread extends Thread
{
    private int FPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel)
    {
        super();
        System.out.println("REACHED THREAD");
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }
    @Override
    public void run()
    {
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount =0;
        long targetTime = 1000/FPS;

        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                synchronized (surfaceHolder) {
                    canvas = this.surfaceHolder.lockCanvas();
                    if(!gamePanel.isGameOver) {
                        this.gamePanel.update();
                        this.gamePanel.draw(canvas);
                    }
                    else
                    {
                        this.setRunning(false);
                    }
                    /*************************************************************
                     * THING TO DO :
                     * CREATE ENEMY OBJECT HERE WITH A SPECIFIED TIME GAP
                     * ENEMY MUST CREATE AFTER EVERY 15 SECONDS
                     * CHECK HOW TO USE SYSTEM TIME VALUE
                     * CALL ENEMY DRAW and UPDATE in GamePanel
                     **************************************************************/
                    //if(System.nanoTime()-startTime)
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == FPS)
            {
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount =0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
    public void setRunning(boolean b)
    {
        running=b;
    }
}