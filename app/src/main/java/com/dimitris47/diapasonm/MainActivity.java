package com.dimitris47.diapasonm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Button> buttons = new ArrayList<>();
    static String[] notes = {"C", "C\u266F/D\u266D", "D", "D\u266F/E\u266D", "E", "F",
            "F\u266F/G\u266D", "G", "G\u266F/A\u266D", "A", "A\u266F/B\u266D", "B"};
    static double[] defFreq = {261.626, 277.183, 293.665, 311.127, 329.626, 349.228,
            369.994, 391.995, 415.305, 440.000, 466.164, 493.883};
    Spinner freqSpinner;
    Spinner durSpinner;
    public static int duration = 1;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        TableLayout table = findViewById(R.id.table);
        freqSpinner = findViewById(R.id.freqSpinner);
        freqSpinner.setSelection(48);
        durSpinner = findViewById(R.id.durSpinner);

        durSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                duration = Integer.parseInt(selected);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        for (int i = 0; i < 12; i++) {
            Button btn = (Button) table.getChildAt(i);
            buttons.add(btn);
            btn.setText(notes[i]);
            int finalI = i;
            btn.setOnClickListener(v -> {
                int spinnerPos = freqSpinner.getSelectedItemPosition();
                new Tone((defFreq[finalI] * (spinnerPos + 392) / 440), duration);
            });
        }
    }

    @Override
    public void onBackPressed() {
        Tone.stopTone();
        this.moveTaskToBack(true);
    }
}
