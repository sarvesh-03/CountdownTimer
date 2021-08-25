package com.example.countdowntimer;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


public class service  extends Service {
    public long currentTime;
    public long TotalTime;
    private CountDownTimer countDownTimer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        currentTime=intent.getLongExtra("c",0);
        TotalTime=intent.getLongExtra("T",0);
        if(currentTime!=0){
            startTimer();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    public void startTimer(){
        countDownTimer=new CountDownTimer(currentTime*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                currentTime--;
                Log.v("Tag",currentTime+"");

            }

            @Override
            public void onFinish() {
                stopSelf();

            }
        }.start();

    }
}
