package com.jimtrinh9985gmail.swingtracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by Kimo on 6/6/2016.
 */
public class ProfileFragment extends Fragment {

    public final String LOG_TAG = ProfileFragment.class.getSimpleName();

    private static int LOAD_IMAGE_RESULTS = 1;
    private ImageView profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.profile_fragment, container, false);

        profileImage = (ImageView) view.findViewById(R.id.photo);
        loadPlayerPicture();

        profileImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.
                        Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, LOAD_IMAGE_RESULTS);
                return false;
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == Activity.RESULT_OK && data != null) {
            File image = new File(Environment.getExternalStorageDirectory() + "/img.jpeg");
            Picasso.with(getActivity())
                    .load(image)
                    .noPlaceholder().centerCrop().fit()
                    .error(R.drawable.player1)
                    .into(profileImage);
        }
    }

    private void loadPlayerPicture() {

    }

    // Insert picture from gallery and save as profile //
    private void insertSavePlayerPicture() {

    }

}
