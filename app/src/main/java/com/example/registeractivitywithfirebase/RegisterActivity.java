package com.example.registeractivitywithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail,etPassword,etUsername;
    Button btnSignup;
    TextView tvBanner;


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();

        tvBanner = findViewById(R.id.banner);
        tvBanner.setOnClickListener(this);

        etEmail = findViewById(R.id.editTextTextEmailAddress);
        etPassword = findViewById(R.id.editTextTextPassword);
        etUsername = findViewById(R.id.editTextTextPersonName);
        btnSignup = findViewById(R.id.btnSignUp);
        btnSignup.setOnClickListener(this);



        };
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,LoginActivty.class));
                break;
            case R.id.btnSignUp:
                registerUser();
                break;

        }

    }

    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String username = etUsername.getText().toString().trim();

        if (username.isEmpty()){
            etUsername.setError("Full Name is Required!");
            etUsername.requestFocus();
            return;
        }
        if (email.isEmpty()){
            etEmail.setError("Email is Required!");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please Provide valid email!");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            etPassword.setError("Password is Required!");
            etPassword.requestFocus();
            return;
        }
        if (password.length() < 6){
            etPassword.setError("Min Password length should be 6 characters!");
            etPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull  Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(username,email,password);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete( Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"User has been registered Successfully",Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(RegisterActivity.this,"Registration is Unsuccessful. Please Try Again",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(RegisterActivity.this,"Registration is Unsuccessful. Please Try Again",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

    }
}
