package com.example.ijobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ijobs.data.User;
import com.example.ijobs.services.UserService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userService = new UserService();
    }

    public void onJobSeekerButtonClick(View view) {
        userService
                .getCurrentUser()
                .addOnSuccessListener(documentSnapshot -> {
                    User user = documentSnapshot.toObject(User.class);
                    if(user.getJobSeekerCv() == null){
                        Intent createJobSeekerProfileIntent = new Intent(this, CreateJobSeekerProfileActivity.class);
                        startActivity(createJobSeekerProfileIntent);
                    }
                    else{
                        Toast.makeText(this, "A mers", Toast.LENGTH_LONG);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "eroare", Toast.LENGTH_LONG);
                });
    }

    public void onJobRecruiterButtonClick(View view) {

    }
}
