package com.example.loginform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    EditText email;
    ProgressBar progressBar;
    Button resetbtn,home;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email=(EditText) findViewById(R.id.forgotemail);
        progressBar=(ProgressBar) findViewById(R.id.progressBar3);
        resetbtn=(Button) findViewById(R.id.resetpassword);
        home=(Button)findViewById(R.id.reset_login);

        resetbtn.setOnClickListener(this);
        home.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.resetpassword:
                resetpassword();
                break;
            case R.id.reset_login:
                startActivity(new Intent(ForgotPassword.this,MainActivity.class));
                break;
        }

    }

    private void resetpassword() {

        String resetemail=email.getText().toString().trim();

        if(resetemail.isEmpty()){
            email.setError("G-Mail Is Required");
            email.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(resetemail).matches()){

            email.setError("Provide Valid G-Mail");
            email.requestFocus();
            return;
        }else{

            progressBar.setVisibility(View.VISIBLE);
            auth=FirebaseAuth.getInstance();
            auth.sendPasswordResetEmail(resetemail).addOnCompleteListener(this,new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ForgotPassword.this, "Please Check Your Email!", Toast.LENGTH_SHORT).show();
                    }else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ForgotPassword.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}