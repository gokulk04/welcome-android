package com.welcome.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.welcome.android.R;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

   /* private final Handler handler = new Handler();

    private final Runnable startActivityRunnable = new Runnable(){
        public void run(){
            Intent intent = new Intent();
            intent.setClass(Splash.this, MainActivity.class);
            startActivity(intent);
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Intent intent = new Intent(Splash.this, MainActivity.class);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(Splash.this, MainActivity.class));
            }
        }, 2000);


    }


}
