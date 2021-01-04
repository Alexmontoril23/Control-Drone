package com.example.controldron;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity{

    private OutputStream outputStream;
    private InputStream inStream;

    private ImageView power;
    private ImageView direction;
    private ImageView puntazo;
    private ImageView rayada;

    private Sender sender;

    private Power powerClass;
    private Direction directionClass;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        removeTopBar();
        setContentView(R.layout.activity_main);
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
        direction = findViewById(R.id.direction);
        power = findViewById(R.id.power);
        puntazo = findViewById(R.id.puntazo);
        rayada = findViewById(R.id.rayada);

        directionClass = new Direction(this);
        powerClass = new Power(this);

        direction.setOnTouchListener(directionClass);
        power.setOnTouchListener(powerClass);

        sender = new Sender();
        sender.start();
    }

    private void removeTopBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setNavigationBarColor(this.getResources().getColor(R.color.colorPrimary));
    }

    private void init() throws IOException {
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {
                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();

                if(bondedDevices.size() > 0) {
                    Object[] devices = (Object[]) bondedDevices.toArray();
                    for (Object o : devices) {
                        Log.d("TAG", o.toString());
                        Log.d("TAG", ((BluetoothDevice) o).getName());
                        if (((BluetoothDevice) o).getAddress().equals("B8:80:4F:3F:66:03")) {
                            BluetoothDevice device = (BluetoothDevice) o;
                            ParcelUuid[] uuids = device.getUuids();
                            BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                            socket.connect();
                            outputStream = socket.getOutputStream();
                            inStream = socket.getInputStream();
                            return;
                        }
                    }
                }
                Log.e("TAG", "No appropriate paired devices.");
            } else {
                Log.e("TAG", "Bluetooth is disabled.");
            }
        }
    }

    public class Sender extends Thread implements Runnable{

        byte theSamePower = 0;
        int power = -1;
        byte theSameDir = 0;

        public Sender(){

        }

        @Override
        public void run() {
            while(true) {
                Log.d("TAG", "" + directionClass.getSend());
                Log.d("TAG", "" + (powerClass.getPower()+225));
                try {
                    outputStream.write(powerClass.getPower() + 225);
                    outputStream.write(directionClass.getSend());
                    outputStream.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        }
    }

    public void run() {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytes = 0;
        int b = BUFFER_SIZE;

        while (true) {
            try {
                bytes = inStream.read(buffer, bytes, BUFFER_SIZE - bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ImageView getPower() {
        return power;
    }

    public ImageView getDirection() {
        return direction;
    }

    public ImageView getPuntazo() {
        return puntazo;
    }

    public Power getPowerClass() {
        return powerClass;
    }

    public ImageView getRayada() {
        return rayada;
    }

    public Direction getDirectionClass() {
        return directionClass;
    }

    public Sender getSender() {
        return sender;
    }
}