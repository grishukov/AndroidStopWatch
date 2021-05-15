package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView textViewTimer;
    private Button buttonStart;
    private int seconds = 0;
    private boolean isRunning = false;
    private boolean wasRunning = false;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTimer = findViewById(R.id.textViewTimer);
        buttonStart = findViewById(R.id.buttonStart);

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            isRunning = savedInstanceState.getBoolean("isRunning");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
            if (isRunning) {
                buttonStart.setText(R.string.continueBtn);
            }
        }
        RunTimer();
    }


    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = wasRunning;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds", seconds);
        outState.putBoolean("isRunning", isRunning);
        outState.putBoolean("wasRunning", wasRunning);

    }

    public void onClickStarTimer(View view) {
        isRunning = true;
        if (isRunning) {
            buttonStart.setText(R.string.continueBtn);
        }
    }

    public void onClickPauseTimer(View view) {
        isRunning = false;
        buttonStart.setText(R.string.continueBtn);
    }

    public void onResetTimer(View view) {
        isRunning = false;
        seconds = 0;
        if (!isRunning) {
            buttonStart.setText(R.string.startBtn);
        }

    }


    public void RunTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                textViewTimer.setText(time);

                if (isRunning) {

                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });


    }
}