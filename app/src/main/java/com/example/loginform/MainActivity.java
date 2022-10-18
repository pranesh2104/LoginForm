package com.example.loginform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
    //Get Id
    EditText g,p;
    ProgressBar progressBar;
    public FirebaseAuth mAuth;
    TextView forget;
    Button login;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        g=(EditText)findViewById(R.id.gmail);
        p=(EditText)findViewById(R.id.Password);
        progressBar=(ProgressBar) findViewById(R.id.progressBar2);
        login=(Button) findViewById(R.id.login);

        // Hide/See Password Work
        checkBox=(CheckBox) findViewById(R.id.checkBox2);
        p.setTransformationMethod(new PasswordTransformationMethod());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b){
                    p.setTransformationMethod(null);
                }else{
                    p.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });


        // Forgot PassWord Work
        forget=(TextView) findViewById(R.id.forgotpassword);
        forget.setOnClickListener(this);

    }
    public void reg(View view) {
         Intent i =new Intent(this,MainActivity2.class);
         startActivity(i);
    }
    public void log_suc(View view) {

        userligin();
    }

    private void userligin() {

        //Get ID values
        String Gmail=g.getText().toString().trim();
        String Password=p.getText().toString().trim();

        if(Gmail.isEmpty() && Password.isEmpty()){
            Toast.makeText(this, "Please Fill All", Toast.LENGTH_SHORT).show();
        }
        else if(Gmail.isEmpty()){
            g.setError("G-Mail Is Required");
            g.requestFocus();
            return;
        }
        else if(Password.isEmpty()){
            p.setError("PassWord Is Required");
            p.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(Gmail).matches()){
            g.setError("Provide Valid G-Mail");
            p.requestFocus();
            return;
        }
        else if(Password.length() < 6){
            p.setError("Required MIN 6 Characters");
            p.requestFocus();
            return;
        }
        else{
            mAuth=FirebaseAuth.getInstance();
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(Gmail,Password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        if(user.isEmailVerified()){
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(MainActivity.this,InfoActivity.class));
                        }else{
                            progressBar.setVisibility(View.GONE);
                            user.sendEmailVerification();
                            Toast.makeText(MainActivity.this, "Please Check Your Email For Verify!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Please Check Your G-Mail/Password", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this,ForgotPassword.class));
    }
}