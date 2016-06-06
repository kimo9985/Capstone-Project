package com.jimtrinh9985gmail.swingtracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import wearprefs.WearPrefs;

public class MainActivity extends AppCompatActivity {

    private Button button1, button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WearPrefs.init(this, "com.swingtracker.FOREHAND");
        WearPrefs.init(this, "com.swingtracker.BACKHAND");
        WearPrefs.init(this, "com.swingtracker.OVERHEAD");

        button1 = (Button) findViewById(R.id.chart_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        button2 = (Button) findViewById(R.id.save_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Save Button!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
