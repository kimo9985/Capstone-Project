package com.jimtrinh9985gmail.swingtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


/**
 * Created by Kimo on 6/6/2016.
 */
public class ProfileFragment extends AppCompatActivity implements View.OnLongClickListener {

    public final String LOG_TAG = ProfileFragment.class.getSimpleName();

    private static int LOAD_IMAGE_RESULTS = 1;
    private static int PROFILE_DATA = 2;
    //private static final String TAG = "result";
    private ImageView profileImage;
    private TextView profileName, profileHeight, profileWeight, profileGrip, profileRacket;
    private String pName, pHeight, pWeight, pGrip, pRacket;
    private String imageUri, returnData;
    private String data, myData;
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_fragment);

        profileImage = (ImageView) findViewById(R.id.photo);
        profileName = (TextView) findViewById(R.id.profile_name);
        profileHeight = (TextView) findViewById(R.id.profile_height);
        profileWeight = (TextView) findViewById(R.id.profile_weight);
        profileGrip = (TextView) findViewById(R.id.profile_grip);
        profileRacket = (TextView) findViewById(R.id.profile_racket);

        loadPlayerProfile();

        profileImage.setOnLongClickListener(this);
        profileName.setOnLongClickListener(this);
        profileHeight.setOnLongClickListener(this);
        profileWeight.setOnLongClickListener(this);
        profileGrip.setOnLongClickListener(this);
        profileRacket.setOnLongClickListener(this);

//        profileImage.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                // Choose profile image from Gallery //
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.
//                        Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, LOAD_IMAGE_RESULTS);
//                return true;
//            }
//        });


    }

    @Override
    public boolean onLongClick(View v) {

        switch (v.getId()) {
            case R.id.photo:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.
                        Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, LOAD_IMAGE_RESULTS);
                break;
            case R.id.profile_name:
                Intent intent1 = new Intent(this, ProfileUpdate.class);
                Log.d(LOG_TAG, "New Text: " + data + " " + returnData);
                startActivityForResult(intent1, PROFILE_DATA);
                //insertProfileData(data);
                UtilityProfile.savePrefProfileName(this, returnData);
                break;
            case R.id.profile_height:
                //insertProfileData(data);
                UtilityProfile.savePrefProfileHeight(this, data);
                break;
            case R.id.profile_weight:
                //insertProfileData(data);
                UtilityProfile.savePrefProfileHeight(this, data);
                break;
            case R.id.profile_grip:
                //insertProfileData(data);
                UtilityProfile.savePrefProfileGrip1(this, data);
                break;
            case R.id.profile_racket:
                //insertProfileData(data);
                UtilityProfile.savePrefProfileRacket(this, data);
                break;
            default:
                break;
        }
        return true;
    }

//    private String insertProfileData(String profileData) {
//        Log.d(LOG_TAG, "insertProfileData!");
//
//
//
//        InputMethodManager imm = (InputMethodManager)
//                getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
//        //imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//
//        //myData = editText.getText().toString();
//        //myData = myData + profileData;
//        //editText.setText(myData);
//
//        Log.d(LOG_TAG, "New Text: " + myData);
//
//        return profileData;


//        EditText textView = null;
//        if (textView != null) {
//            textView.setText(profileData);
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData().toString();
            Log.d(LOG_TAG, "onActivityResult 1: " + imageUri + " " + data);
            UtilityProfile.saveImageUri(this, imageUri);

            Picasso.with(this)
                    .load(imageUri)
                    .noPlaceholder().centerCrop().fit()
                    .error(R.drawable.player1)
                    .into(profileImage);
        }

        if (requestCode == PROFILE_DATA && requestCode == Activity.RESULT_OK && data != null) {
            returnData = data.getStringExtra("result");
            Log.d(LOG_TAG, "onActivityResult 2: " + returnData + " " + data);
        }
    }

    private void loadPlayerProfile() {

        imageUri = UtilityProfile.getPrefImageUri(this);
        Picasso.with(this)
                .load(imageUri)
                .noPlaceholder().centerCrop().fit()
                .error(R.drawable.player1)
                .into(profileImage);

        pName = UtilityProfile.getPrefProfileName(this);
        pHeight = UtilityProfile.getPrefProfileHeight(this);
        pWeight = UtilityProfile.getPrefProfileWeight(this);
        pGrip = UtilityProfile.getPrefProfileGrip1(this);
        pRacket = UtilityProfile.getPrefProfileRacket(this);
    }
}
