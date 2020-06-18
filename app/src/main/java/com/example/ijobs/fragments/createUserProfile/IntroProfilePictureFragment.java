package com.example.ijobs.fragments.createUserProfile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.ijobs.CreateUserProfileActivity;
import com.example.ijobs.R;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IntroProfilePictureFragment extends Fragment {

    private FloatingActionButton takePictureButton;
    private BottomAppBar materialToolbar;
    private ImageView profilePicturePlaceholder;
    private Bitmap profilePictureBitmap;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int TAKE_PICTURE_CODE = 1002;
    private static final int REQUEST_CAMERA_PERMISSION = 1003;
    private static final int REQUEST_READ_STORAGE_PERMISSION = 1001;
    private Button nextButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_user_profile_intro_profile_picture, container, false);

        initializeComponents(view);

        return view;
    }

    private void initializeComponents(View view) {
        materialToolbar = view.findViewById(R.id.create_user_profile_intro_picture_BottomBar);
        takePictureButton = view.findViewById(R.id.create_user_profile_take_picture_button);
        profilePicturePlaceholder = view.findViewById(R.id.create_user_profile_picture_preview);
        nextButton = view.findViewById(R.id.create_user_profile_nextButton);

        takePictureButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            {
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            }
            else{
                capturePicture();
            }
        });

        materialToolbar.setNavigationOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};

                    getActivity().requestPermissions(permissions, REQUEST_READ_STORAGE_PERMISSION);
                } else {
                    pickImageFromGallery();
                }
            } else {
                pickImageFromGallery();

            }
        });

        nextButton.setOnClickListener(v -> {
            CreateUserProfileActivity activity = (CreateUserProfileActivity) getActivity();
            activity.setFormProfilePicture(profilePictureBitmap);
            activity.finalizeProfile();
        });
    }

    private void capturePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE_CODE);
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_READ_STORAGE_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            case REQUEST_CAMERA_PERMISSION : {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    capturePicture();
                }
                else{
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TAKE_PICTURE_CODE){
            profilePictureBitmap = (Bitmap) data.getExtras().get("data");
            profilePicturePlaceholder.setImageBitmap(profilePictureBitmap);
            nextButton.setVisibility(View.VISIBLE);
        }

        if(requestCode == IMAGE_PICK_CODE){
            profilePicturePlaceholder.setImageURI(data.getData());
            nextButton.setVisibility(View.VISIBLE);
        }
    }

    //    private boolean ValidateInput() {
//        boolean isValid = true;
//
//        if (phoneNumberField.getText().toString().isEmpty()) {
//            phoneNumberField.setError("Specificati un numar de telefon");
//            isValid = false;
//        }
//
//        return isValid;
//    }
}
