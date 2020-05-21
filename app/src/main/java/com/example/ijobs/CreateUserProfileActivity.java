package com.example.ijobs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ijobs.fragments.createUserProfile.CreateUserProfilePagerAdapter;
import com.example.ijobs.services.UserService;
import com.example.ijobs.viewmodels.UserProfileViewModel;
import com.google.android.gms.tasks.Task;

import java.util.Date;

public class CreateUserProfileActivity extends AppCompatActivity {

    private ViewPager contentPager;
    private PagerAdapter contentAdapter;
    protected UserProfileViewModel form;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user_profile);

        form = new UserProfileViewModel();
        userService = new UserService();

        initializeComponents();
    }

    public void setFormBirthdate(Date birthDate) {
        form.setBirthDate(birthDate);
    }

    public void goToStep(int step) {
        contentPager.setCurrentItem(step);
    }

    public void finalizeProfile() {
        Task<Void> finalizeProfileResult = userService.updateUserProfile(this.form);

        if (finalizeProfileResult == null) {
            Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG);
            return;
        }

        finalizeProfileResult
                .addOnSuccessListener(aVoid -> {
                    Intent goToMainScreen = new Intent(this, MainActivity.class);
                    goToMainScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goToMainScreen);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG);
                    return;
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
}
