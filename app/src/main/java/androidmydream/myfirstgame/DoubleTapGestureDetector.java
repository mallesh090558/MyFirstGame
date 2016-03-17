package androidmydream.myfirstgame;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Madhuri on 3/17/2016.
 */
public class DoubleTapGestureDetector extends GestureDetector.SimpleOnGestureListener {

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d("TAG", "Double Tap Detected ...");
        System.out.println("DOUBLETAP GESTURE DETECTED");
        if(GamePanel.isGameOver)
        {
            GamePanel.RestarttheGame=true;
        }

        return true;
    }

}
