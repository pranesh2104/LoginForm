package com.example.loginform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoActivity extends AppCompatActivity {


    private FirebaseUser user;
    private DatabaseReference reference;
    private String userid;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        final TextView name= findViewById(R.id.info_name);
        final TextView email=findViewById(R.id.info_email);
        final TextView dob= findViewById(R.id.info_dob);
        final TextView gender= findViewById(R.id.info_gender);
        final TextView welcome= findViewById(R.id.welcome);
        progressBar=findViewById(R.id.progressBar4);

        progressBar.setVisibility(View.VISIBLE);
        user=FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Member");
        userid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        reference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User profile = snapshot.getValue(User.class);
                if (profile != null) {
                    String Name =profile.Name;
                    String DOB = profile.DOB;
                    String Gmail = profile.Gmail;
                    String Gender = profile.Gender;
                    progressBar.setVisibility(View.GONE);

                    welcome.setText("Welcome, " + Name + " !");
                    name.setText(Name);
                    dob.setText(DOB);
                    email.setText(Gmail);
                    gender.setText(Gender);

                    Toast.makeText(InfoActivity.this, userid, Toast.LENGTH_SHORT).show();

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(InfoActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InfoActivity.this, "Something Went Wrong!", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void signout(View view) {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(InfoActivity.this,MainActivity.class));
    }

    public void update_activity(View view) {
        startActivity(new Intent(InfoActivity.this,update_profile.class));
    }
}