package com.example.loginform;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;

public class update_profile extends AppCompatActivity {

    EditText up_name,up_dob;
    Button b;
    private RadioButton gender;
    private RadioGroup radioGroup;
    User member;
    ProgressBar progressBar;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        up_name=(EditText) findViewById(R.id.up_name);
        up_dob=(EditText) findViewById(R.id.up_dob);
        b=(Button) findViewById(R.id.up_but) ;

        progressBar=(ProgressBar)findViewById(R.id.up_progressBar);


        up_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar calendar=Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog=new DatePickerDialog(
                        update_profile.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        up_dob.setText(day+"/"+(month+1)+"/"+year);

                    }
                },year,month,day);
                datePickerDialog.show();

            }

        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                update();
            }

        });


    }

    private void update() {


        String Name=up_name.getText().toString();
        String DOB = up_dob.getText().toString();

        radioGroup = (RadioGroup) findViewById(R.id.up_radiogroup);
        int selected = radioGroup.getCheckedRadioButtonId();

        gender =(RadioButton) findViewById(selected);

        String Gender = gender.getText().toString();

        if(Name.isEmpty() && DOB.isEmpty()){

            Toast.makeText(this, "Please Fill All", Toast.LENGTH_SHORT).show();
        }

        if(Name.isEmpty()){
            up_name.setError("Name is required!");
        }

        else{

            progressBar.setVisibility(View.VISIBLE);
            FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
            String userid= user.getUid();
//            String const_mail =reference.child(userid).child("Gmail").addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Member profile=snapshot.getValue(Member.class);
//                    if(profile != null){
//                        String Gmail=profile.Gmail;
//                        member = new Member(Name,DOB,Gender,Gmail);
//                        FirebaseDatabase.getInstance().getReference("Member").child(userid).setValue(member).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//
//                                    up_name.setText("");
//                                    up_dob.setText("");
//                                    progressBar.setVisibility(View.INVISIBLE);
//                                    Toast.makeText(update_profile.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
//                                }
//                                else{
//                                    Toast.makeText(update_profile.this,"Update Failed!!",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//                }
//
//
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });

            //final DocumentReference doc=db.collection("Member").document(userid);





        }
    }

}