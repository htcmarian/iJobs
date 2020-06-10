package com.example.ijobs.fragments.createUserProfile;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CreateUserProfilePagerAdapter extends FragmentPagerAdapter {


    public CreateUserProfilePagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case CreateUserProfileSteps.IntroBirthdateFragment:
                return new IntroBirthdateFragment();
            case CreateUserProfileSteps.IntroAddressFragment:
                return new IntroAddressFragment();
            case CreateUserProfileSteps.IntroPhoneNumberFragment:
                return new IntroPhoneNumberFragment();
            case CreateUserProfileSteps.IntroProfilePictureFragment:
                return new IntroProfilePictureFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return CreateUserProfileSteps.length();
    }
}
