package com.example.registrationcommodity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Password extends AppCompatActivity {

    private EditText resetEmail;
    private Button reset;
    FirebaseAuth rAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        resetEmail = (EditText) findViewById(R.id.email_reset);
        reset = (Button) findViewById(R.id.reset);
        
        rAuth = FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = resetEmail.getText().toString().trim();
        if(email.isEmpty()){
            resetEmail.setError("Put an email!");
            resetEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            resetEmail.setError("Please provide the correct email!");
            resetEmail.requestFocus();
            return;
        }

        rAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast toast = Toast.makeText(Password.this,"Check your email",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(Password.this,"An error has occurred!",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                    toast.show();
                }
            }
        });
    }
}