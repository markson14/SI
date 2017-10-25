package comp5216.sydney.edu.au.SI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.google.cloud.android.speech.R;

/**
 * Created by marksonzhang on 22/10/17.
 */

public class Introduction extends AppCompatActivity {

    float x1, x2, y1 ,y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction);

    }

    public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1=touchevent.getX();
                y1=touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2=touchevent.getX();
                y2=touchevent.getY();
                if(x1>x2){
                    Intent i = new Intent(Introduction.this, MainActivity.class);
                    startActivities(new Intent[]{i});

                }
                break;
        }
        return false;
    }
}
