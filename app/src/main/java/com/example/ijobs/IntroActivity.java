package com.example.ijobs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.ijobs.services.AuthService;

public class IntroActivity extends AppCompatActivity {

    private AuthService authService;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        initializeComponents();

        authService = new AuthService();

        redirectToActivity();
    }

    private void initializeComponents() {
        loadingSpinner = findViewById(R.id.mainActivity_loadingSpinner);
    }

    private void redirectToActivity(){
        if(authService.getUser() == null){
            Intent authActivityIntent = new Intent(this, AuthActivity.class);
            authActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(authActivityIntent);
        }
        else{
            Intent mainActivityIntent = new Intent(this, MainActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(mainActivityIntent);
        }
    }
}
