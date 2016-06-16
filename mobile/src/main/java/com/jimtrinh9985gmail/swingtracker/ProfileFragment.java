package com.jimtrinh9985gmail.swingtracker;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Kimo on 6/6/2016.
 */
public class ProfileFragment extends AppCompatActivity implements View.OnLongClickListener {

    public final String LOG_TAG = ProfileFragment.class.getSimpleName();

    private ImageView profileImage;
    private TextView profileName, profileHeight, profileWeight, profileGrip, profileRacket;
    private String pName, pHeight, pWeight, pGrip, pRacket;
    private String imageUri, returnData;

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

        profileName.setText(pName);
        profileHeight.setText(pHeight);
        profileWeight.setText(pWeight);
        profileGrip.setText(pGrip);
        profileRacket.setText(pRacket);

        profileImage.setOnLongClickListener(this);
        profileName.setOnLongClickListener(this);
        profileHeight.setOnLongClickListener(this);
        profileWeight.setOnLongClickListener(this);
        profileGrip.setOnLongClickListener(this);
        profileRacket.setOnLongClickListener(this);
    }

    @Override
    public boolean onLongClick(View v) {

        switch (v.getId()) {
            case R.id.photo:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.
                        Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
                break;
            case R.id.profile_name:
                Intent intent1 = new Intent(this, ProfileUpdate.class);
                startActivityForResult(intent1, 1);
                break;
            case R.id.profile_height:
                Intent intent2 = new Intent(this, ProfileUpdate.class);
                startActivityForResult(intent2, 2);
                break;
            case R.id.profile_weight:
                Intent intent3 = new Intent(this, ProfileUpdate.class);
                startActivityForResult(intent3, 3);
                break;
            case R.id.profile_grip:
                Intent intent4 = new Intent(this, ProfileUpdate.class);
                startActivityForResult(intent4, 4);
                break;
            case R.id.profile_racket:
                Intent intent5 = new Intent(this, ProfileUpdate.class);
                startActivityForResult(intent5, 5);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                imageUri = data.getData().toString();
                UtilityProfile.saveImageUri(this, imageUri);

                Picasso.with(this)
                        .load(imageUri)
                        .noPlaceholder().centerCrop().fit()
                        .error(R.drawable.player1)
                        .into(profileImage);
            }
            if (requestCode == 1) {
                returnData = data.getExtras().getString("result");
                profileName.setText(returnData);
                UtilityProfile.savePrefProfileName(this, returnData);
            }
            if (requestCode == 2) {
                returnData = data.getExtras().getString("result");
                profileHeight.setText(returnData);
                UtilityProfile.savePrefProfileHeight(this, returnData);
            }
            if (requestCode == 3) {
                returnData = data.getExtras().getString("result");
                profileWeight.setText(returnData);
                UtilityProfile.savePrefProfileWeight(this, returnData);
            }
            if (requestCode == 4) {
                returnData = data.getExtras().getString("result");
                profileGrip.setText(returnData);
                UtilityProfile.savePrefProfileGrip1(this, returnData);
            }
            if (requestCode == 5) {
                returnData = data.getExtras().getString("result");
                profileRacket.setText(returnData);
                UtilityProfile.savePrefProfileRacket(this, returnData);
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_profile:
                return true;
            case R.id.menu_chart:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
