package com.example.registeractivitywithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivty extends AppCompatActivity {
    TextView TvRegister;

     EditText etemail;
     EditText etpassword;
    private Button login;

    Button btnLogin;


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activty);

        mAuth = FirebaseAuth.getInstance();

        TvRegister = findViewById(R.id.Register);
        etemail = findViewById(R.id.etEmail);
        etpassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emails = etemail.getText().toString();
                String passwords = etpassword.getText().toString();
                if (emails.isEmpty()){
                    etemail.setError("Please enter email address");
                    etemail.requestFocus();
                }
                else if (passwords.isEmpty()){
                    etpassword.setError("Please enter your password");
                    etpassword.requestFocus();
                }
                else if (emails.isEmpty() && passwords.isEmpty()){
                    Toast.makeText(LoginActivty.this,"Fields are Empty!",Toast.LENGTH_LONG).show();
                }
                else if (!(emails.isEmpty() && passwords.isEmpty())){
                    mAuth.signInWithEmailAndPassword(emails,passwords).addOnCompleteListener(LoginActivty.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(LoginActivty.this,"Login Error, Please Try Again",Toast.LENGTH_LONG).show();
                            }else{
                                Intent i = new Intent(LoginActivty.this,MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivty.this,"Error Occurred!",Toast.LENGTH_LONG).show();
                }


            }
        });

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged( FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if (mFirebaseUser !=null){
                    Toast.makeText(LoginActivty.this,"You are logged in",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivty.this,MainActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivty.this,"Please Login",Toast.LENGTH_LONG).show();
                }
            }
        };


        TvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivty.this,RegisterActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onStart(){
        super.onStart();

    }
    }
