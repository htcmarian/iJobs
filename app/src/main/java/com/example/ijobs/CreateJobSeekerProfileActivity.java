package com.example.ijobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import com.example.ijobs.fragments.createJobSeeker.CreateJobSeekerPagerAdapter;
import com.example.ijobs.repositories.UserRepository;
import com.example.ijobs.services.UserService;
import com.example.ijobs.viewmodels.JobSeekerProfileViewModel;
import com.example.ijobs.viewmodels.ServiceOfferListItemViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.Date;
import java.util.List;

public class CreateJobSeekerProfileActivity extends AppCompatActivity {

    private ViewPager contentPager;
    private PagerAdapter contentAdapter;
    protected JobSeekerProfileViewModel form;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_job_seeker_profile);

        form = new JobSeekerProfileViewModel();
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
        Task<Void> finalizeProfileResult = userService.updateJobSeekerCv(this.form);

        if (finalizeProfileResult == null) {
            Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG);
            return;
        }

        finalizeProfileResult
                .addOnSuccessListener(aVoid -> {
                    Intent goToJobOfferIntent = new Intent(this, JobOfferActivity.class);
                    goToJobOfferIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(goToJobOfferIntent);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "A intervenit o eroare", Toast.LENGTH_LONG);
                    return;
                });
    }

    private void initializeComponents() {
        contentPager = findViewById(R.id.createJobSeekerProfileViewPager);
        contentAdapter = new CreateJobSeekerPagerAdapter(getSupportFragmentManager());

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

    public void setFormServicesOffered(List<ServiceOfferListItemViewModel> selectedServices) {
        form.setServicesOffered(selectedServices);
    }

    public void setFormName(String name) {
        form.setName(name);
    }
}
