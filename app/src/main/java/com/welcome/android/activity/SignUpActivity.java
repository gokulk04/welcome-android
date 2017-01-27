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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.welcome.android.R;
import com.welcome.android.objects.User;
import com.welcome.android.utils.FirebaseAuthUtils;
import com.welcome.android.utils.FirebaseDBUtils;

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

                final User newUser = new User();
                FirebaseDBUtils<User> fdbu = new FirebaseDBUtils<User>(User.class);
                newUser.setRef(fdbu.getNewChildRef());

                newUser.setName(editName.getText().toString());
                newUser.setEmail(editEmail.getText().toString());

                newUser.pushToDB().continueWith(new Continuation<Void, Task<AuthResult>>() {
                    @Override
                    public Task then(@NonNull Task task) throws Exception {
                        return FirebaseAuthUtils.signUp(newUser, editPassword.getText().toString());
                    }
                }).continueWith(new Continuation<AuthResult, Task<User>>() {
                    @Override
                    public Task<User> then(@NonNull Task<AuthResult> task) throws Exception {
                        FirebaseAuthUtils.currentFirebaseAuth = task.getResult().getUser();
                        regToOccupation.putExtra("password", editPassword.getText().toString());
                        return User.getById(FirebaseAuthUtils.currentFirebaseAuth.getUid());
                    }
                }).continueWith(new Continuation<User, Void>() {
                    @Override
                    public Void then(@NonNull Task<User> task) throws Exception {
                        FirebaseAuthUtils.currentUser = task.getResult();
                        startActivity(regToOccupation);
                        return null;
                    }
                });
            default:
                break;
        }
    }
}
