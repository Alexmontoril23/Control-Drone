package com.example.controldron;

import android.view.MotionEvent;
import android.view.View;

public class Direction implements View.OnTouchListener {

    private MainActivity mainActivity;
    private int localX, localY;
    private int sendX, sendY;
    private char send = (char) (7*15 + 7);
    private float puntazoX, puntazoY;

    public Direction(MainActivity activity){
        this.mainActivity = activity;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            localX = (int) (mainActivity.getDirection().getX());
            localY = (int) (mainActivity.getDirection().getY());
        }if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
            setPos(motionEvent);
        }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            mainActivity.getPuntero().animate().y(localY + mainActivity.getDirection().getHeight()/2f - mainActivity.getPuntero().getWidth()/2f).x(localX + mainActivity.getDirection().getWidth()/2f - mainActivity.getPuntero().getWidth()/2f);
            sendY = 0;
            sendX = 0;
            send = (char) (7*15 + 7);
        }
        return true;
    }

    private void setPos(MotionEvent motionEvent){
        if(belowR(motionEvent.getRawX()-localX-mainActivity.getDirection().getWidth()/2f, motionEvent.getRawY()-localY-mainActivity.getDirection().getHeight()/2f)){
            puntazoX = motionEvent.getRawX() - mainActivity.getPuntero().getWidth()/2f;
            puntazoY = motionEvent.getRawY() - mainActivity.getPuntero().getWidth()/2f;
            mainActivity.getPuntero().setX(puntazoX);
            mainActivity.getPuntero().setY(puntazoY);
            sendX = (int) (motionEvent.getRawX()  - localX)*15/755;
            sendY = (int) (motionEvent.getRawY()  - localY)*15/755;
            send = (char) ((sendY-1)*15 + sendX-1);
        }
    }

    private boolean belowR(float x, float y){
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2)) < 350;
    }

    public int getSend() {
        return send;
    }
}
