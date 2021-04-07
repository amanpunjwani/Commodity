package com.example.registrationcommodity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.FirestoreGrpc;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser;
    private EditText emailLog, passwordLog;
    private Button loginButton;

    private FirebaseAuth loginAuth;
    private TextView forgotPasssword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginAuth = FirebaseAuth.getInstance();

        forgotPasssword = (TextView) findViewById(R.id.for_pass);
        forgotPasssword.setOnClickListener(this);

        registerUser = (TextView) findViewById(R.id.register);
        registerUser.setOnClickListener(this);


        emailLog = (EditText) findViewById(R.id.emailLogin);
        passwordLog = (EditText) findViewById(R.id.passwordLogin);

        loginButton = (Button) findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, Register.class));
                break;

            case R.id.loginBtn:
                userLogin();
                break;
            case R.id.for_pass:
                startActivity(new Intent(this, Password.class));
                break;
        }

    }

    private void userLogin() {
        String email = emailLog.getText().toString().trim();
        String password = passwordLog.getText().toString().trim();

        if(email.isEmpty()){
            emailLog.setError("Put an email!");
            emailLog.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLog.setError("Please provide the correct email!");
            emailLog.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordLog.setError("Put a password in!");
            passwordLog.requestFocus();
            return;
        }

        if(password.length() <= 6){
            passwordLog.setError("Put a password more than 6 characters, mate!");
            passwordLog.requestFocus();
            return;
        }

        loginAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if(user.isEmailVerified()){
                        Toast.makeText(MainActivity.this,"Logged in!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this,"Check your email", Toast.LENGTH_LONG).show();
                    }


                }else{
                    Toast.makeText(MainActivity.this, "Failed to login, please retry", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}