package com.welcome.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.welcome.android.R;
import com.welcome.android.utils.FirebaseAuthUtils;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar mToolbar;
    TextView txtRegToLogin;
    Button btnSignUp;
    EditText editName, editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtRegToLogin = (TextView) this.findViewById(R.id.txtRegToLogin);
        txtRegToLogin.setOnClickListener(this);

        editName = (EditText) this.findViewById(R.id.editName);
        editEmail = (EditText) this.findViewById(R.id.editEmail);
        editPassword = (EditText) this.findViewById(R.id.editPassword);

        btnSignUp = (Button) this.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.txtRegToLogin:
                Intent regToLogin = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(regToLogin);
                break;
            case R.id.btnSignUp:
                final Intent regToOccupation = new Intent(SignUpActivity.this, OccupationActivity.class);
                FirebaseAuthUtils.signUp(editEmail.getText().toString(), editName.getText().toString(), editPassword.getText().toString()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        startActivity(regToOccupation);
                    }
                });
                break;
            default:
                break;
        }
    }
}
