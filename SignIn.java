package com.example.to_do_list_dailytaskplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

public class SignIn extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        TextView newuser;
        EditText email,password;
        Button signin,google;
        email = findViewById(R.id.signinemail);
        password = findViewById(R.id.signinpassword);
        signin = findViewById(R.id.signinnormalbtn);
        newuser = findViewById(R.id.newclick);
        dialog = new ProgressDialog(SignIn.this);
        dialog.setTitle("Logging In");
        dialog.setMessage("Please Wait");
        newuser.setOnClickListener(new View.OnClickListener() {
            Intent redirect = new Intent(SignIn.this,SignUp.class);
            @Override
            public void onClick(View v) {
             startActivity(redirect);
             finish();
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().isEmpty()&&!password.getText().toString().isEmpty())
                {   dialog.show();
                    mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                Toast.makeText(SignIn.this, "Login Successful!!", Toast.LENGTH_SHORT).show();
                                                Intent mainpage1 = new Intent(SignIn.this,MainActivity.class);
                                                startActivity(mainpage1);
                                                finish();
                                            }
                                        },4000);
                                    }
                                    else
                                    {   new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            Toast.makeText(SignIn.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    },3000);

                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(SignIn.this, "All Fields Are Required!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(mAuth.getCurrentUser()!=null)
        {
            Intent exists1 = new Intent(SignIn.this,MainActivity.class);
            startActivity(exists1);
            finish();
        }
    }
}