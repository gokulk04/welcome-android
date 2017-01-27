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

public class OccupationActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;

    private EditText editOccupation, editSchool, editMajor, editYear;
    private boolean isStudent = true;
    private Button btnNext1to2;

//    private Bundle imports;

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
//            Bundle imports = getIntent().getExtras();
//
//            PASSWORD = (String) imports.get("password");

        //Toast.makeText(OccupationActivity.this, PASSWORD, Toast.LENGTH_LONG).show();
//        Toast.makeText(OccupationActivity.this, newUser.getName().toString(), Toast.LENGTH_LONG).show();

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Additional Info")
//                .setMessage("We need some additional info from you before we can sign you up. Don't worry, you'll only have to enter this once!")
//                .setCancelable(false)
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                });
//
//        AlertDialog alert = builder.create();
//        alert.show();
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
                                    editOccupation.setText(occupations[which]);

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
                final Intent occupationToAddInfo = new Intent(OccupationActivity.this, AdditionalInfoActivity.class);

                /*final User newUser = FirebaseAuthUtils.currentUser;
                newUser.setJobTitle(editOccupation.getText().toString());
                newUser.setSchool(editSchool.getText().toString());
                newUser.setMajor(editMajor.getText().toString());
                newUser.setYear(editYear.getText().toString());
                newUser.pushToDB().continueWith(new Continuation<Void, Task<AuthResult>>() {
                    @Override
                    public Task<AuthResult> then(@NonNull Task<Void> task) throws Exception {
                        return FirebaseAuthUtils.signUp(newUser, PASSWORD);
                    }
                }).continueWith(new Continuation<AuthResult, Void>() {
                    @Override
                    public Void then(@NonNull Task task) throws Exception {
                        return null;
                    }
                });*/
                startActivity(occupationToAddInfo);

                break;

            case R.id.editOccupation:

//                final String occupations[] = {"Student", "Professional"};
//                AlertDialog.Builder builder = new AlertDialog.Builder(OccupationActivity.this);
//                builder.setTitle("Occupation")
//                        .setItems(occupations, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                editOccupation.setText(occupations[which]);
//                            }
//                        });
//                builder.create();
                break;

            default:
                break;
        }
    }
}
