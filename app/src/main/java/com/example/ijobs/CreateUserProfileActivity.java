package com.example.ijobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ijobs.fragments.createUserProfile.CreateUserProfilePagerAdapter;
import com.example.ijobs.services.AuthService;
import com.example.ijobs.services.UserService;
import com.example.ijobs.viewmodels.UserProfileViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

import java.util.Date;

public class CreateUserProfileActivity extends AppCompatActivity {

    private ViewPager contentPager;
    private PagerAdapter contentAdapter;
    protected UserProfileViewModel form;
    private UserService userService;
    private AuthService authService;
    private Bitmap userProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_profile);

        form = new UserProfileViewModel();
        userService = new UserService();
        authService = new AuthService();

        initializeComponents();
    }

    public void setFormBirthdate(Date birthDate) {
        form.setBirthDate(birthDate);
    }

    public void goToStep(int step) {
        contentPager.setCurrentItem(step);
    }

    public void finalizeProfile() {
        FirebaseUser user = authService.getUser();

        userService
                .uploadUserProfilePicture(user.getUid().toString(), userProfilePicture)
                .addOnSuccessListener(v -> {
                    Task<Void> finalizeProfileResult = userService.updateUserProfile(this.form);

                    if (finalizeProfileResult == null) {
                        Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG).show();
                        return;
                    }

                    finalizeProfileResult
                            .addOnSuccessListener(aVoid -> {
                                Intent goToMainScreen = new Intent(this, MainActivity.class);
                                goToMainScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(goToMainScreen);
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG).show();
                            });
                })
                .addOnFailureListener(v -> {
                    Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG).show();
                });
    }

    private void initializeComponents() {
        contentPager = findViewById(R.id.createJobSeekerProfileViewPager);
        contentAdapter = new CreateUserProfilePagerAdapter(getSupportFragmentManager());

        contentPager.setAdapter(contentAdapter);
    }

    public void setFormAddress(String address, String city, String postalCode) {
        form.setAddress(address);
        form.setCity(city);
        form.setPostalCode(postalCode);
    }

    public void setFormPhoneNumber(String phoneNumber) {
        form.setPhoneNumber(phoneNumber);
    }

    public void setFormName(String name) {
        form.setName(name);
    }

    public void setFormProfilePicture(Bitmap profilePictureBitmap) {
        userProfilePicture = profilePictureBitmap;
    }
}
