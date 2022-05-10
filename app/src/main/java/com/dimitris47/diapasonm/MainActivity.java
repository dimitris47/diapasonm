package com.dimitris47.diapasonm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Button> buttons = new ArrayList<>();
    static double[] defFreq = {261.626, 277.183, 293.665, 311.127, 329.626, 349.228,
            369.994, 391.995, 415.305, 440.000, 466.164, 493.883};
    Spinner freqSpinner;
    Spinner durSpinner;
    Button stopBtn;
    public static int duration = 1;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        int freqSpinnerPos = sharedPref.getInt("freqPos", 48);
        int durSpinnerPos = sharedPref.getInt("durPos", 0);

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TableLayout table = findViewById(R.id.table);
        freqSpinner = findViewById(R.id.freqSpinner);
        freqSpinner.setSelection(freqSpinnerPos);
        durSpinner = findViewById(R.id.durSpinner);
        durSpinner.setSelection(durSpinnerPos);
        stopBtn = findViewById(R.id.stopBtn);

        freqSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences freqPref = com.dimitris47.diapasonm.MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = freqPref.edit();
                editor.putInt("freqPos", position);
                editor.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        durSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                duration = Integer.parseInt(selected.split(" sec")[0]);
                SharedPreferences durPref = com.dimitris47.diapasonm.MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = durPref.edit();
                editor.putInt("durPos", position);
                editor.apply();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        buttons.addAll(Arrays.asList(findViewById(R.id.button0),
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9),
                findViewById(R.id.button10),
                findViewById(R.id.button11)));

        for (int i = 0; i < 12; i++) {
            int finalI = i;
            buttons.get(i).setOnClickListener(v -> {
                int spinnerPos = freqSpinner.getSelectedItemPosition();
                new Tone((defFreq[finalI] * (spinnerPos + 392) / 440), duration);
            });
        }

        stopBtn.setOnClickListener(v -> Tone.stopTone());
    }

    @Override
    public void onBackPressed() {
        Tone.stopTone();
        this.moveTaskToBack(true);
    }
}
