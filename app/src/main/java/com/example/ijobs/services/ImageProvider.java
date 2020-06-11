package com.example.ijobs.services;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageProvider {
    public static Uri getLocalImageUri(String imageName){
        return Uri.parse("android.resource://" + "com.example.ijobs" + "/drawable/" + imageName);
    }

    public static StorageReference getUserProfilePicture(String userId){
        FirebaseStorage storageService = FirebaseStorage.getInstance();

        return storageService.getReference().child("/profilePictures/"+userId+".jpeg");
    }
}
