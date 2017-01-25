package com.welcome.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.welcome.android.R;
import com.welcome.android.objects.User;

public class OccupationActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;

    private EditText editOccupation, editSchool, editMajor, editYear;

    private Button btnNext1to2;

    private Bundle imports;

    private User newUser;
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

//        newUser = (User) imports.getSerializable("newUser");
//        PASSWORD = imports.getString("password");

//        newUser = (User) imports.getSerializableExtra("newUser");

//        PASSWORD = imports.getExtras().getString("password");

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
                Intent occupationToAddInfo = new Intent(OccupationActivity.this, AdditionalInfoActivity.class);

//                newUser.setJobTitle(editOccupation.getText().toString());
//                newUser.setSchool(editSchool.getText().toString());

//                newUser.setMajor(editMajor.getText().toString());
//                newUser.setYear(editYear.getText().toString());

//                newUser.pushToDB();
//                FirebaseAuthUtils.signUp(newUser, PASSWORD);

                startActivity(occupationToAddInfo);
                break;
            default:
                break;
        }
    }
}
