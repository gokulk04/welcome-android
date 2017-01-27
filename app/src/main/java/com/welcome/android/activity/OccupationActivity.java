package com.welcome.android.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.welcome.android.R;
import com.welcome.android.utils.FirebaseAuthUtils;

public class OccupationActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;

    private EditText editOccupation, editSchool, editMajor, editYear;
    private boolean isStudent = true;
    private Button btnNext1to2;

    private String PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupation);
               mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final String occupations[] = {"Student", "Professional"};
        editSchool = (EditText) this.findViewById(R.id.editSchool);
        editMajor = (EditText) this.findViewById(R.id.editMajor);
        editYear = (EditText) this.findViewById(R.id.editYear);

        editSchool.setVisibility(View.INVISIBLE);
        editSchool.setHint("");
        editMajor.setVisibility(View.INVISIBLE);
        editMajor.setHint("");
        editYear.setVisibility(View.INVISIBLE);
        editMajor.setHint("");


        editOccupation = (EditText) this.findViewById(R.id.editOccupation);
        editOccupation.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    AlertDialog.Builder occuDialog = new AlertDialog.Builder(OccupationActivity.this);
                    occuDialog.setTitle("Occupation")
                            .setItems(occupations, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0){
                                        editSchool.setVisibility(View.VISIBLE);
                                        editSchool.setHint("School");
                                        editMajor.setVisibility(View.VISIBLE);
                                        editMajor.setHint("Major");
                                        editYear.setVisibility(View.VISIBLE);
                                        editYear.setHint("Year");

                                    }else{
                                        editSchool.setVisibility(View.VISIBLE);
                                        editSchool.setHint("Company");
                                        editMajor.setVisibility(View.VISIBLE);
                                        editMajor.setHint("Title");
                                        editYear.setVisibility(View.INVISIBLE);
                                        editYear.setHint("");
                                    }
                                    String occupation = occupations[which];
                                    editOccupation.setText(occupation);

                                    dialog.dismiss();

                                }
                            });
                    AlertDialog occuAlert = occuDialog.create();
                    occuAlert.show();
                }

            }
        });



        btnNext1to2 = (Button) this.findViewById(R.id.btnNext1to2);
        btnNext1to2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnNext1to2:
                FirebaseAuthUtils.currentUser.setOccupation(editOccupation.getText().toString());
                final Intent occupationToAddInfo = new Intent(OccupationActivity.this, AdditionalInfoActivity.class);
                startActivity(occupationToAddInfo);
                break;

            case R.id.editOccupation:
                break;

            default:
                break;
        }
    }
}
