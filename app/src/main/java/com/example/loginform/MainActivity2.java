package com.example.loginform;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class MainActivity2 extends AppCompatActivity {

    EditText rn,db,ma,pass;
    private RadioButton gender;
    private RadioGroup radioGroup;
    User member;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    CheckBox checkBox;
    DatePickerDialog.OnDateSetListener setListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Get ID
        rn = (EditText) findViewById(R.id.name); //Username
        db = (EditText) findViewById(R.id.dob);    //Date Of Birth
        ma = (EditText) findViewById(R.id.email);   //Email
        pass = (EditText) findViewById(R.id.Password); //Password

        progressBar=(ProgressBar)findViewById(R.id.progressBar);


        // Checkbox
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        pass.setTransformationMethod(new PasswordTransformationMethod());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked){
                    pass.setTransformationMethod(null);
                }
                else{
                    pass.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
        // Date Picker For DateOfBirth
        db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar=Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        db.setText(day+"/"+(month+1)+"/"+year);

                    }
                },year,month,day);
                datePickerDialog.show();

                }

        });

    }


        public void reg_suc (View view){

            register();

        }

    private void register() {

        //Get ID values
        String Name = rn.getText().toString().trim();   //Username Value
        String DOB = db.getText().toString().trim();   //DOB value
        String Gmail = ma.getText().toString().trim();   //Email value
        String Password = pass.getText().toString().trim();

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        int selected = radioGroup.getCheckedRadioButtonId();

        gender =(RadioButton) findViewById(selected);

        String Gender = gender.getText().toString();

        if (Name.isEmpty() && DOB.isEmpty() && Gmail.isEmpty() && Password.isEmpty())
        {
            Toast.makeText(this, "Please Fill All", Toast.LENGTH_SHORT).show();

        }
        else if (Name.isEmpty())
        {
            rn.setError("Username is Requried");
            rn.requestFocus();
            return;
        }
        else if (DOB.isEmpty())
        {
            db.setError("DateOfBirth is Requried");
            db.requestFocus();
            return;
        }
        else if (Gmail.isEmpty())
        {
            ma.setError("GMail is Requried");
            ma.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(Gmail).matches())
        {
            ma.setError("Provide Valid G-Mail");
            ma.requestFocus();
            return;
        }
        else if (Password.isEmpty())
        {
            pass.setError("Password is Requried");
            pass.requestFocus();
            return;
        }
        else if (Password.length() < 6)
        {
            pass.setError("Required MIN 6 Characters");
            pass.requestFocus();
            return;
        } else
        {

            mAuth = FirebaseAuth.getInstance();
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(Gmail, Password)
                    .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                member = new User(Name, Gmail, DOB,Gender);
                                FirebaseDatabase.getInstance().getReference("Member")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            rn.setText("");
                                            db.setText("");
                                            pass.setText("");
                                            ma.setText("");
                                            Toast.makeText(MainActivity2.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        } else {
                                            Toast.makeText(MainActivity2.this, "Registration Failed!!", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(MainActivity2.this, "This Email Already Exist!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                        }
                    });
        }
    }

    public void log (View view){
            Intent j = new Intent(this, MainActivity.class);
            startActivity(j);
        }

}