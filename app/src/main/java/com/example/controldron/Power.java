package com.example.controldron;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;

public class Power implements View.OnTouchListener {

    private char power = 0;
    private MainActivity activity;

    private int localX, localY;

    public Power(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(MotionEvent.ACTION_DOWN == motionEvent.getAction()) {

            localX = (int) (activity.getPower().getX() + activity.getRayada().getWidth()/2f);
            localY = (int) (activity.getPower().getY() + activity.getRayada().getHeight()/2f);

        }else if(MotionEvent.ACTION_MOVE == motionEvent.getAction()){
            if(motionEvent.getRawY() > activity.getPower().getY() &&
                    motionEvent.getRawY() < activity.getPower().getY() + activity.getPower().getHeight() - activity.getRayada().getHeight()){
                activity.getRayada().setY(motionEvent.getRawY());
            }
            power = (char) ((char) Math.max((int)((1000-motionEvent.getRawY())*30/976),0));
        }

        return false;
    }

    public int getPower() {
        return power;
    }
}
