package com.example.ijobs.services;

import android.net.Uri;

public  class ImageProvider {
    public static Uri getImageUri(String imageName){
        return Uri.parse("android.resource://" + "com.example.ijobs" + "/drawable/" + imageName);
    }
}
