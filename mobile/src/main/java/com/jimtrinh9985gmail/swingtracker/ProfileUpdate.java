package com.jimtrinh9985gmail.swingtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Kimo on 6/9/2016.
 */
public class ProfileUpdate extends AppCompatActivity {

    public final String LOG_TAG = ProfileFragment.class.getSimpleName();

    public Button button;
    public EditText profileUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_update);

        profileUpdate = (EditText) findViewById(R.id.profile_update);
        button = (Button) findViewById(R.id.profile_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                String result = profileUpdate.getText().toString();
                returnIntent.putExtra("result", result);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
