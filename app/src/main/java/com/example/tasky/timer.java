package com.example.tasky;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class timer extends AppCompatActivity {
    TextView texthrs;
    TextView textmins;
    TextView textsecs ;
    Button button ;
    int sec  ;
    int min ;
    int hr ;
    Button add1min ;
    Button finished;
    DatabaseHelper database = new DatabaseHelper(this);
    CountDownTimer countdownTimer;

    int points ;
    private MediaPlayer mediaPlayer;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", timer.MODE_PRIVATE);

        finished = findViewById(R.id.button2);
        add1min = findViewById(R.id.button25);
        Intent intent = getIntent();
        int hrs = Integer.parseInt(intent.getStringExtra("hrs"));
        int initial_hrs = hrs;
        int mins = Integer.parseInt(intent.getStringExtra("mins"));
        int posn =intent.getIntExtra("posn",-1);
        int id  = intent.getIntExtra("id",-1);
        int secs = Integer.parseInt(intent.getStringExtra("secs"));
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Intent intent2 = new Intent(timer.this,MainActivity.class);
                database.deleteContact(id);
                int x = 0 ;
                x = initial_hrs*10 + 5;
                points =  sharedPreferences.getInt("points",0)+ x ;
                editor.putInt("points", points);
                editor.apply();
                Toast.makeText(timer.this, "+ " + points + "points" , Toast.LENGTH_SHORT).show();
                startActivity(intent2);

            }
        });

        String number = intent.getStringExtra("number");
        min = mins;
        hr = hrs;
        sec = secs ;
        texthrs = findViewById(R.id.textView5);
        textmins = findViewById(R.id.textView6);
        textsecs = findViewById(R.id.textView7);
        button = findViewById(R.id.button24);
        texthrs.setText(String.valueOf(hr));
        textmins.setText(String.valueOf(min));
        textsecs.setText(String.valueOf(sec));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {;

                contactmodel1 model = new contactmodel1();
                model.id = id;
                model.name = texthrs.getText()+":"+textmins.getText()+":"+ textsecs.getText();
                model.fame = number ;
                database.updateContact(model);
                Intent intent1 = new Intent(timer.this,MainActivity.class);
                startActivity(intent1);

            }
        });


        countdownTimer =  new CountDownTimer(hr*3600000+min*60000+sec*1000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                sec--;
                if(sec == -1){
                    sec= 59;
                    min--;
                    textmins.setText(String.valueOf(min));
                }
                textsecs.setText(String.valueOf(sec));
                if(min == -1){
                    min= 59;
                    textmins.setText(String.valueOf(min));
                    hr--;
                    texthrs.setText(String.valueOf(hr));
                }

            }

            @Override
            public void onFinish() {


            }
        }.start();

        add1min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (countdownTimer != null) {
                    countdownTimer.cancel();
                }
                hr = Integer.parseInt((String) texthrs.getText());
                sec =Integer.parseInt( (String) textsecs.getText() );
                min =Integer.parseInt((String) textmins.getText());
                if(min == 59){
                    min = 0;
                    hr ++;
                }
                else{
                    min++;
                }
                texthrs.setText(String.valueOf(hr));
                textmins.setText(String.valueOf(min));
                textsecs.setText(String.valueOf(sec));
                countdownTimer =  new CountDownTimer(hr*3600000+min*60000+sec*1000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        sec--;
                        if(sec == -1){
                            sec= 59;
                            min--;
                            textmins.setText(String.valueOf(min));
                        }
                        textsecs.setText(String.valueOf(sec));
                        if(min == -1){
                            min= 59;
                            textmins.setText(String.valueOf(min));
                            hr--;
                            texthrs.setText(String.valueOf(hr));
                        }


                    }
                    @Override
                    public void onFinish() {
                        mediaPlayer = MediaPlayer.create(timer.this, R.raw.timer3);
                        if (mediaPlayer != null) {
                            mediaPlayer.start();
                        }


                    }
                }.start();
            }
        });

    }
}