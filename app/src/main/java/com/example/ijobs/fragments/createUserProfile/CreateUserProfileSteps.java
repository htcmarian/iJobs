package com.example.ijobs.fragments.createUserProfile;

public class CreateUserProfileSteps {
    public static final int IntroBirthdateFragment = 0;
    public static final int IntroAddressFragment = 1;
    public static final int IntroPhoneNumberFragment = 2;
    public static final int IntroProfilePictureFragment = 3;

    public static int length(){
        return CreateUserProfileSteps.class.getFields().length;
    }
}
