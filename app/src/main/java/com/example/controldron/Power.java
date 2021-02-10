package com.example.controldron;

import android.view.MotionEvent;
import android.view.View;

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

            localX = (int) (activity.getPower().getX() + activity.getPotenciaIV().getWidth()/2f);
            localY = (int) (activity.getPower().getY() + activity.getPotenciaIV().getHeight()/2f);

        }else if(MotionEvent.ACTION_MOVE == motionEvent.getAction()){
            if(motionEvent.getRawY() > activity.getPower().getY() &&
                    motionEvent.getRawY() < activity.getPower().getY() + activity.getPower().getHeight() - activity.getPotenciaIV().getHeight()){
                activity.getPotenciaIV().setY(motionEvent.getRawY());
            }
            power = (char) ((char) Math.max((int)((1000-motionEvent.getRawY())*30/976),0));
        }

        return false;
    }

    public int getPower() {
        return power;
    }
}
