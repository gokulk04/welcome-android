package com.welcome.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.welcome.android.R;
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
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch(id){
            case R.id.btnLogin:
                String email = editLoginEmail.getText().toString();
                String password = editLoginPassword.getText().toString();
                FirebaseAuthUtils.login(email, password).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LogInActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Intent loginToOcc = new Intent(LogInActivity.this, OccupationActivity.class);
                        startActivity(loginToOcc);
                    }
                });
                break;
            default:
                break;
        }
    }
}
