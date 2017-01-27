package com.welcome.android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.welcome.android.DatePickerFrag;
import com.welcome.android.R;
import com.welcome.android.utils.FirebaseAuthUtils;

public class AdditionalInfoActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    Button btnNext2to3;
    private EditText editBirthday, editEthnicity, editGender, editNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_info);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String ethnicities[] = {"White/Caucasian", "Black/African American", "Asian/Pacific Islander", "American Indian or Alaskan Native", "Other"};
        final String genders[] = {"Male", "Female", "Other"};

        editBirthday = (EditText) this.findViewById(R.id.editBirthday);
        editNumber = (EditText) this.findViewById(R.id.editNumber);
        editEthnicity = (EditText) this.findViewById(R.id.editEthnicity);
        editGender = (EditText) this.findViewById(R.id.editGender);

        editEthnicity.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    AlertDialog.Builder raceDialog = new AlertDialog.Builder(AdditionalInfoActivity.this);
                    raceDialog.setTitle("Ethnicity")
                            .setItems(ethnicities, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String ethnicity = ethnicities[which];
                                    editEthnicity.setText(ethnicity);
                                    dialog.dismiss();

                                }
                            });
                    AlertDialog raceAlert = raceDialog.create();
                    raceAlert.show();
                }
            }
        });

        editGender.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    AlertDialog.Builder sexDialog = new AlertDialog.Builder(AdditionalInfoActivity.this);
                    sexDialog.setTitle("Gender")
                            .setItems(genders, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String gender = genders[which];
                                    editGender.setText(gender);
                                    dialog.dismiss();

                                }
                            });
                    AlertDialog sexAlert = sexDialog.create();
                    sexAlert.show();
                }
            }
        });

        new DatePickerFrag(AdditionalInfoActivity.this, R.id.editBirthday);

        btnNext2to3 = (Button) this.findViewById(R.id.btnNext2to3);
        btnNext2to3.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnNext2to3:
                FirebaseAuthUtils.currentUser.setGender(editGender.getText().toString());
                FirebaseAuthUtils.currentUser.setBirthday(editBirthday.getText().toString());
                FirebaseAuthUtils.currentUser.setRace(editEthnicity.getText().toString());
                FirebaseAuthUtils.currentUser.setPhoneNumber(editNumber.getText().toString());
                Intent AddInfoToProfPic = new Intent(AdditionalInfoActivity.this, ProfilePictureActivity.class);
                startActivity(AddInfoToProfPic);
                break;
            default:
                break;
        }
    }

}
