package com.example.ijobs.fragments.createJobSeeker;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ijobs.viewmodels.JobSeekerProfileViewModel;

public class CreateJobSeekerPagerAdapter extends FragmentPagerAdapter {



    public CreateJobSeekerPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case CreateJobSeekerSteps.IntroBirthdateFragment:
                return new IntroBirthdateFragment();
            case CreateJobSeekerSteps.IntroAddressFragment:
                return new IntroAddressFragment();
            case CreateJobSeekerSteps.IntroPhoneNumberFragment:
                return new IntroPhoneNumberFragment();
            case CreateJobSeekerSteps.IntroServicesFragment:
                return new IntroServicesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return CreateJobSeekerSteps.length();
    }
}
