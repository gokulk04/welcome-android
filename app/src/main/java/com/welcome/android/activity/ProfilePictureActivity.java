package com.welcome.android.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.welcome.android.R;
import com.welcome.android.utils.FirebaseAuthUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ProfilePictureActivity extends AppCompatActivity implements View.OnClickListener {
    private final int SELECT_PHOTO = 1;
    Toolbar mToolbar;
    private ImageView imgProfile;
    private Bitmap selectedImage;
    private Task<Void> updateProfileImageTask;

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
                        selectedImage = BitmapFactory.decodeStream(imageStream);
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
                if (updateProfileImageTask == null) {
                    Toast.makeText(ProfilePictureActivity.this, "no image has been uploaded yet", Toast.LENGTH_LONG).show();
                } else {
                    final Intent profPicToWelcome = new Intent(ProfilePictureActivity.this, WelcomeActivity.class);
                    updateProfileImageTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfilePictureActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            startActivity(profPicToWelcome);
                        }
                    });
                }
                break;
            case R.id.btnUploadPic:
                Intent imgIntent = new Intent (Intent.ACTION_PICK);
                imgIntent.setType("image/*");
                startActivityForResult(imgIntent, SELECT_PHOTO);
                if (selectedImage == null) {
                    Toast.makeText(ProfilePictureActivity.this, "no image selected", Toast.LENGTH_LONG).show();
                } else {
                    updateProfileImageTask = FirebaseAuthUtils.updateProfile(FirebaseAuthUtils.currentUser.getName(), selectedImage);
                }
                break;
            default:
                break;
        }
    }
}
