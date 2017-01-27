package com.welcome.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.welcome.android.R;
import com.welcome.android.objects.User;
import com.welcome.android.utils.FirebaseAuthUtils;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editLoginEmail, editLoginPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editLoginEmail = (EditText) this.findViewById(R.id.editLoginEmail);
        editLoginPassword = (EditText) this.findViewById(R.id.editLoginPassword);
        btnLogin = (Button) this.findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.btnLogin:
                String email = editLoginEmail.getText().toString();
                String password = editLoginPassword.getText().toString();
                FirebaseAuthUtils.login(email, password).continueWith(new Continuation<AuthResult, Task<User>>() {
                    @Override
                    public Task<User> then(@NonNull Task<AuthResult> task) throws Exception {
                        FirebaseUser user = task.getResult().getUser();
                        FirebaseAuthUtils.currentFirebaseAuth = user;
                        return User.getById(user.getUid());
                    }
                }).continueWith(new Continuation<User, Void>() {
                    @Override
                    public Void then(@NonNull Task<User> task) throws Exception {
                        FirebaseAuthUtils.currentUser = task.getResult();
                        Intent loginToOcc = new Intent(LogInActivity.this, OccupationActivity.class);
                        startActivity(loginToOcc);
                        return null;
                    }
                });
                break;
            default:
                break;
        }
    }
}
