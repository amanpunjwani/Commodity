package com.example.registrationcommodity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth regAuth;
    private FirebaseFirestore addUser;

    private TextView banner;
    private EditText editFullName, editAge, editEmail, editPassword, editPhone;
    private Button reg;
    private String userID;

    private RadioGroup buttons;

    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        banner = (TextView) findViewById(R.id.CommodityBanner);
        banner.setOnClickListener(this);

        editFullName = (EditText) findViewById(R.id.fullname);
        editAge = (EditText) findViewById(R.id.age);
        editEmail = (EditText) findViewById(R.id.email);
        editPassword = (EditText) findViewById(R.id.password);
        editPhone = (EditText) findViewById(R.id.number);

        reg = (Button) findViewById(R.id.registerUser);
        reg.setOnClickListener(this);

        regAuth = FirebaseAuth.getInstance();
        addUser = FirebaseFirestore.getInstance();

        buttons = (RadioGroup) findViewById(R.id.radioGroup);
        buttons.clearCheck();

        buttons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                text = rb.getText().toString().trim();
                Toast toast = Toast.makeText(Register.this,text,Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                toast.show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.CommodityBanner:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerUser:
                userRegister();
                break;
        }
    }

    private void userRegister() {

        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();
        String name = editFullName.getText().toString().trim();
        String age = editAge.getText().toString().trim();
        String num = editPhone.getText().toString().trim();

        if(name.isEmpty()){
            editFullName.setError("Full name is required!");
            editFullName.requestFocus();
            return;
        }

        if(num.isEmpty()){
            editPhone.setError("Please enter a phone number!");
            editPhone.requestFocus();
            return;
        }

        if(num.length()<10){
            editPhone.setError("Enter a proper phone number!");
            editPhone.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("Please provide the correct email!");
            editEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editPassword.setError("Password is empty!");
            editPassword.requestFocus();
            return;
        }

        if(password.length()<=6){
            editPassword.setError("Less than 6 mate!!");
            editPassword.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editAge.setError("Please enter an age!");
            editAge.requestFocus();
            return;
        }


        regAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            if(text.equals("User")){
                                User user = new User(name, num, email, num);
                                userID = regAuth.getCurrentUser().getUid();
                                DocumentReference dRef =  addUser.collection("Users").document(userID);
                                Map<String, User> userToAdd = new HashMap<>();
                                userToAdd.put(userID, user);

                                dRef.set(userToAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast toast = Toast.makeText(Register.this,"User has been registered successfully",Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                            toast.show();
                                            //progressBar.setVisibility(View.GONE);
                                        }
                                        else{
                                            Toast toast = Toast.makeText(Register.this,"User was not registered successfully",Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                            toast.show();
                                            //progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }
                            else if(text.equals("Non Profit Organization")) {
                                NPOrg nporganization = new NPOrg(name, num, email, num);
                                userID = regAuth.getCurrentUser().getUid();
                                DocumentReference dRef =  addUser.collection("Non-Profit Organization").document(userID);
                                Map<String, NPOrg> userToAdd = new HashMap<>();
                                userToAdd.put(userID, nporganization);

                                dRef.set(userToAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast toast = Toast.makeText(Register.this,"Non-profit organization has been registered successfully",Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                            toast.show();
                                            //progressBar.setVisibility(View.GONE);
                                        }
                                        else{
                                            Toast toast = Toast.makeText(Register.this,"Non-profit organization was not registered successfully",Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                            toast.show();
                                            //progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }
                            else if(text.equals("Volunteer")){
                                Volunteer volunteer = new Volunteer(name, num, email, num);
                                userID = regAuth.getCurrentUser().getUid();
                                DocumentReference dRef =  addUser.collection("Volunteer").document(userID);
                                Map<String, Volunteer> userToAdd = new HashMap<>();
                                userToAdd.put(userID, volunteer);

                                dRef.set(userToAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast toast = Toast.makeText(Register.this,"Volunteer has been registered successfully",Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                            toast.show();
                                            //progressBar.setVisibility(View.GONE);
                                        }
                                        else{
                                            Toast toast = Toast.makeText(Register.this,"Volunteer was not registered successfully",Toast.LENGTH_LONG);
                                            toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                                            toast.show();
                                            //progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });

                            }
                        }
                        else{
                            Toast toast = Toast.makeText(Register.this,"Person was not registered successfully",Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
                            toast.show();
                            //progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

}