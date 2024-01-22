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

import com.example.to_do_list_dailytaskplanner.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        EditText name, email, password;
        Button signup, google;
        TextView existclicks;
        existclicks = findViewById(R.id.existclick);
        existclicks.setOnClickListener(new View.OnClickListener() {
            Intent loginredirect = new Intent(SignUp.this, SignIn.class);

            @Override
            public void onClick(View v) {
                startActivity(loginredirect);
                finish();
            }
        });
        dialog = new ProgressDialog(this);
        dialog.setTitle("Signing Up");
        dialog.setMessage("Please Wait");

        name = findViewById(R.id.signupname);
        email = findViewById(R.id.signupemail);
        password = findViewById(R.id.signuppassword);
        signup = findViewById(R.id.signupnormalbtn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!name.getText().toString().equals("")&&!email.getText().toString().equals("")&&!password.getText().toString().equals("")) {
                    dialog.show();
                    mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        Users users = new Users(name.getText().toString(), email.getText().toString(), password.getText().toString());
                                        String Uid = task.getResult().getUser().getUid();
                                        database.getReference().child("Users").child(Uid).setValue(users);
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dialog.dismiss();
                                                Toast.makeText(SignUp.this, "SignUp Successful!1", Toast.LENGTH_SHORT).show();
                                                Intent redirect = new Intent(SignUp.this, SignIn.class);
                                                startActivity(redirect);
                                                finish();

                                            }
                                        },4000);
                                    }
                                    else
                                    {   new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            Toast.makeText(SignUp.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    },3000);

                                    }


                                }
                            });
                }
                else
                {
                    Toast.makeText(SignUp.this, "All Fields Are Required!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(mAuth.getCurrentUser()!=null)
        {
            Intent exists;
            exists = new Intent(SignUp.this,SignIn.class);
                    startActivity(exists);
                    finish();
        }
    }
}
