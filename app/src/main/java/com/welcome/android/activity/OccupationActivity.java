package com.welcome.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.welcome.android.R;
import com.welcome.android.objects.User;
import com.welcome.android.utils.FirebaseAuthUtils;

public class OccupationActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;

    private EditText editOccupation, editSchool, editMajor, editYear;

    private Button btnNext1to2;

    private Bundle imports;

    private String PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_occupation);
               mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imports = getIntent().getExtras();

        PASSWORD = imports.getString("password");

//        Toast.makeText(OccupationActivity.this, PASSWORD, Toast.LENGTH_LONG).show();
//        Toast.makeText(OccupationActivity.this, newUser.getName().toString(), Toast.LENGTH_LONG).show();

        editOccupation = (EditText) this.findViewById(R.id.editOccupation);
        editSchool = (EditText) this.findViewById(R.id.editSchool);
        editMajor = (EditText) this.findViewById(R.id.editMajor);
        editYear = (EditText) this.findViewById(R.id.editYear);

        btnNext1to2 = (Button) this.findViewById(R.id.btnNext1to2);
        btnNext1to2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnNext1to2:
                final Intent occupationToAddInfo = new Intent(OccupationActivity.this, AdditionalInfoActivity.class);

                final User newUser = FirebaseAuthUtils.currentUser;
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
                        startActivity(occupationToAddInfo);
                        return null;
                    }
                });
                break;
            default:
                break;
        }
    }
}
