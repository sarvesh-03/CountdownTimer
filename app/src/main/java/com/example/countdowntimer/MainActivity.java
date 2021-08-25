package com.example.countdowntimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {
    private Dialog dialog;
    private TextView time;
    private Button SetTime;
    private Integer HH;
    private Integer MM;
    private Integer SS;
    private long Time;
    private Button Play;
    private long CurrentTime;
    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;
    private ImageView restart;
    private ImageView add10;
    private ImageView sub10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Play=findViewById(R.id.play_pause);
        SetTime=findViewById(R.id.button);
        //SetTime.setVisibility(View.INVISIBLE);
        Play.setVisibility(View.INVISIBLE);

        progressBar=findViewById(R.id.progressBar2);
        restart=findViewById(R.id.restart);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restart();
            }
        });
        add10=findViewById(R.id.add10);
        add10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add10();
            }
        });
        sub10=findViewById(R.id.sub10);
        sub10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sub10();
            }
        });

        Play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Play.getText().toString().equals("Play")){
                startTimer();
                Play.setText("Pause");
                }
                else {
                    PauseTimer();
                }
            }
        });
        dialog=new Dialog(this);
        dialog.setContentView(R.layout.dialogue_box);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        time=findViewById(R.id.textView);

        SetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TextView)dialog.findViewById(R.id.ss)).setText("00s");
                ((TextView)dialog.findViewById(R.id.mm)).setText("00m");
                ((TextView)dialog.findViewById(R.id.hh)).setText("00h");
                dialog.show();
            }
        });
        dialog.findViewById(R.id.uph).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer=Integer.parseInt(((TextView)dialog.findViewById(R.id.hh)).getText().toString().substring(0,2));

                Log.v("tag",""+integer+((TextView)dialog.findViewById(R.id.hh)).getText().toString());
                integer++;
                ((TextView)dialog.findViewById(R.id.hh)).setText(String.format("%02d",integer)+"h");
            }
        });
        dialog.findViewById(R.id.downh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer=Integer.parseInt(((TextView)dialog.findViewById(R.id.hh)).getText().toString().substring(0,2));

                Log.v("tag",""+integer+((TextView)dialog.findViewById(R.id.hh)).getText().toString());
                if(integer>0){
                    integer--;
                }
                else integer=0;
                ((TextView)dialog.findViewById(R.id.hh)).setText(String.format("%02d",integer)+"h");

            }
        });
        dialog.findViewById(R.id.upm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer=Integer.parseInt(((TextView)dialog.findViewById(R.id.mm)).getText().toString().substring(0,2));

                Log.v("tag",""+integer+((TextView)dialog.findViewById(R.id.mm)).getText().toString());
                if(integer<59){
                    integer++;
                }
                else integer=0;
                ((TextView)dialog.findViewById(R.id.mm)).setText(String.format("%02d",integer)+"m");
            }
        });
        dialog.findViewById(R.id.downm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer=Integer.parseInt(((TextView)dialog.findViewById(R.id.mm)).getText().toString().substring(0,2));

                Log.v("tag",""+integer+((TextView)dialog.findViewById(R.id.mm)).getText().toString());
                if(integer>0){
                    integer--;
                }
                else integer=59;
                ((TextView)dialog.findViewById(R.id.mm)).setText(String.format("%02d",integer)+"m");

            }
        });
        dialog.findViewById(R.id.ups).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer=Integer.parseInt(((TextView)dialog.findViewById(R.id.ss)).getText().toString().substring(0,2));

                Log.v("tag",""+integer+((TextView)dialog.findViewById(R.id.ss)).getText().toString());
                if(integer<59){
                    integer++;
                }
                else integer=0;
                ((TextView)dialog.findViewById(R.id.ss)).setText(String.format("%02d",integer)+"s");

            }
        });
        dialog.findViewById(R.id.downs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer integer=Integer.parseInt(((TextView)dialog.findViewById(R.id.ss)).getText().toString().substring(0,2));

                Log.v("tag",""+integer+((TextView)dialog.findViewById(R.id.ss)).getText().toString());
                if(integer>0){
                    integer--;
                }
                else integer=59;
                ((TextView)dialog.findViewById(R.id.ss)).setText(String.format("%02d",integer)+"s");

            }
        });
        dialog.findViewById(R.id.set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  setTime(((TextView)dialog.findViewById(R.id.hh)).getText().toString().substring(0,2),((TextView)dialog.findViewById(R.id.mm)).getText().toString().substring(0,2),((TextView)dialog.findViewById(R.id.ss)).getText().toString().substring(0,2));
                  dialog.hide();
            }
        });
        dialog.findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();

            }
        });

    }

    private void startTimer() {
        if(progressBar.getProgress()==100) {
            isStarted=true;
            SetTime.setVisibility(View.INVISIBLE);
            countDownTimer = new CountDownTimer(Time * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    if (CurrentTime > 0)
                        CurrentTime--;
                    HH = (int) CurrentTime / 3600;
                    MM = (int) (CurrentTime - HH * 3600) / 60;
                    SS = (int) (CurrentTime % 3600) % 60;
                    progressBar.setProgress((int) (CurrentTime * 100 / Time));
                    time.setText(String.format("%02d", HH) + ":" + String.format("%02d", MM) + ":" + String.format("%02d", SS));


                }

                @Override
                public void onFinish() {
                    SetTime.setVisibility(View.VISIBLE);
                    progressBar.setProgress(100);
                    CurrentTime=Time;
                    HH = (int) CurrentTime / 3600;
                    MM = (int) (CurrentTime - HH * 3600) / 60;
                    SS = (int) (CurrentTime % 3600) % 60;
                    time.setText(String.format("%02d", HH) + ":" + String.format("%02d",MM ) + ":" + String.format("%02d", SS));
                    Play.setText("Play");
                    isStarted=false;
                }
            }.start();
        }
        else {
            countDownTimer = new CountDownTimer(CurrentTime * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    if (CurrentTime > 0)
                        CurrentTime--;
                    HH = (int) CurrentTime / 3600;
                    MM = (int) (CurrentTime - HH * 3600) / 60;
                    SS = (int) (CurrentTime % 3600) % 60;
                    progressBar.setProgress((int) (CurrentTime * 100 / Time));
                    time.setText(String.format("%02d", HH) + ":" + String.format("%02d", MM) + ":" + String.format("%02d", SS));

                }

                @Override
                public void onFinish() {
                    SetTime.setVisibility(View.VISIBLE);
                    progressBar.setProgress(100);
                    CurrentTime=Time;
                    HH = (int) CurrentTime / 3600;
                    MM = (int) (CurrentTime - HH * 3600) / 60;
                    SS = (int) (CurrentTime % 3600) % 60;
                    time.setText(String.format("%02d", HH) + ":" + String.format("%02d",MM ) + ":" + String.format("%02d", SS));
                    Play.setText("Play");
                    isStarted=false;

                }
            }.start();

        }



    }
    private boolean isStarted=false;

    public void setTime(String hh,String mm,String ss){
        time.setText(hh+":"+mm+":"+ss);
        SetTime.setVisibility(View.INVISIBLE);
        HH=Integer.parseInt(hh);
        MM=Integer.parseInt(mm);
        SS=Integer.parseInt(ss);
        Time= HH*3600+MM*60+SS;
        Play.setVisibility(View.VISIBLE);
        CurrentTime=Time;
        if(Time==0) {
            progressBar.setProgress(0);
            SetTime.setVisibility(View.VISIBLE);
            Play.setVisibility(View.INVISIBLE);
        }
        else
        progressBar.setProgress(100);
    }

    public void PauseTimer(){
        if(countDownTimer!=null)
        {
            countDownTimer.cancel();
            countDownTimer=null;
        }
        Play.setText("Play");
    }
    public void Add10(){
        if(isStarted) {
            //PauseTimer();
            if (CurrentTime < Time - 10) {
                CurrentTime += 10;
            } else CurrentTime = Time;
            progressBar.setProgress((int) (CurrentTime * 100 / Time));
            HH = (int) CurrentTime / 3600;
            MM = (int) (CurrentTime - HH * 3600) / 60;
            SS = (int) (CurrentTime % 3600) % 60;
            time.setText(String.format("%02d", HH) + ":" + String.format("%02d", MM) + ":" + String.format("%02d", SS));
            //Play.performClick();
        }

    }
    public void Sub10(){
        if(isStarted) {
            //PauseTimer();
            if (CurrentTime > 10) {
                CurrentTime -= 10;
                progressBar.setProgress((int) (CurrentTime * 100 / Time));
                HH = (int) CurrentTime / 3600;
                MM = (int) (CurrentTime - HH * 3600) / 60;
                SS = (int) (CurrentTime % 3600) % 60;
                time.setText(String.format("%02d", HH) + ":" + String.format("%02d", MM) + ":" + String.format("%02d", SS));
                //Play.performClick();
            } else
            {   PauseTimer();
                SetTime.setVisibility(View.VISIBLE);
                progressBar.setProgress(100);
                CurrentTime=Time;
                HH = (int) CurrentTime / 3600;
                MM = (int) (CurrentTime - HH * 3600) / 60;
                SS = (int) (CurrentTime % 3600) % 60;
                time.setText(String.format("%02d", HH) + ":" + String.format("%02d",MM ) + ":" + String.format("%02d", SS));
                Play.setText("Play");
                isStarted=false;
            }

        }

    }
    public void Restart(){
        if(isStarted) {
            //PauseTimer();
            progressBar.setProgress(100);
            CurrentTime = Time;
            HH = (int) CurrentTime / 3600;
            MM = (int) (CurrentTime - HH * 3600) / 60;
            SS = (int) (CurrentTime % 3600) % 60;
            time.setText(String.format("%02d", HH) + ":" + String.format("%02d", MM) + ":" + String.format("%02d", SS));
            //Play.performClick();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent=new Intent(MainActivity.this,service.class);
        intent.putExtra("C",CurrentTime);
        intent.putExtra("T",Time);
        startService(intent);
    }
}