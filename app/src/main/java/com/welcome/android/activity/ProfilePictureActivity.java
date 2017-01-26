package com.welcome.android.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.welcome.android.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ProfilePictureActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar mToolbar;
    private final int SELECT_PHOTO = 1;
    private ImageView imgProfile;

    private Button btnNext3, btnUploadPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgProfile = (ImageView) this.findViewById(R.id.imgProfile);

        btnUploadPic = (Button) this.findViewById(R.id.btnUploadPic);
        btnUploadPic.setOnClickListener(this);

        btnNext3 = (Button) this.findViewById(R.id.btnNext3);
        btnNext3.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        imgProfile.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnNext3:
                Intent profPicToWelcome = new Intent(ProfilePictureActivity.this, WelcomeActivity.class);
                startActivity(profPicToWelcome);
                break;
            case R.id.btnUploadPic:
                Intent imgIntent = new Intent (Intent.ACTION_PICK);
                imgIntent.setType("image/*");
                startActivityForResult(imgIntent, SELECT_PHOTO);
                //Firebase add user info for profile picture **NOTE PROFILE PIC UPLOAD NOT WORKING PROPERLY


                break;
            default:
                break;
        }

    }
}
