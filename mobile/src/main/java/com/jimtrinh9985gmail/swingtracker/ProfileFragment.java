package com.jimtrinh9985gmail.swingtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


/**
 * Created by Kimo on 6/6/2016.
 */
public class ProfileFragment extends AppCompatActivity {

    public final String LOG_TAG = ProfileFragment.class.getSimpleName();

    private static int LOAD_IMAGE_RESULTS = 1;
    private ImageView profileImage;
    private TextView profileName, profileHeight, profileWeight, profileGrip, profileRacket;
    private String pName, pHeight, pWeight, pGrip, pRacket;
    private String imageUri;

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

        profileImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Choose profile image from Gallery //
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.
                        Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, LOAD_IMAGE_RESULTS);
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData().toString();

            UtilityProfile.saveImageUri(this, imageUri);

            Picasso.with(this)
                    .load(imageUri)
                    .noPlaceholder().centerCrop().fit()
                    .error(R.drawable.player1)
                    .into(profileImage);
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
