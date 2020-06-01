package com.example.ijobs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ijobs.data.User;
import com.example.ijobs.services.AuthService;
import com.example.ijobs.services.UserService;
import com.example.ijobs.viewmodels.UserProfileViewModel;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Map;

public class AuthActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private TextView errorTextMessage;

    private AuthService authService;
    private UserService userService;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initializeComponents();

        authService = new AuthService();
        userService = new UserService();

        FacebookSdk.sdkInitialize(AuthActivity.this);
        //AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onLoginButtonClick(View view) {
        boolean isUserInputValid = ValidateUserInput();

        if (isUserInputValid) {
            final String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            authService
                    .login(email, password)
                    .addOnSuccessListener(authResult -> NavigateToMainScreen())
                    .addOnFailureListener(e -> {
                        errorTextMessage.setText("Username si/sau parola gresita");
                    });
        }
    }

    public void onRegisterButtonClick(View view) {
        boolean isUserInputValid = ValidateUserInput();

        if (isUserInputValid) {
            final String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            authService.register(email, password)
                    .addOnSuccessListener(authResult -> {
                        FirebaseUser user = authService.getUser();
                        String userId = user.getUid();

                        saveUserToDatabase(email, userId);
                    })
                    .addOnFailureListener(e -> {
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            errorTextMessage.setText("Exista deja un cont asociat email-ului");
                        }
                    });
        }
    }

    private void saveUserToDatabase(String email, String userId) {
        userService
                .createUser(userId, email)
                .addOnSuccessListener(aVoid -> NavigateToCreateUserProfileScreen())
                .addOnFailureListener(e -> errorTextMessage.setText("A intervenit o eroare. Incercati din nou"));
    }

    private void NavigateToMainScreen() {
        Intent navigateToOfferListIntent = new Intent(AuthActivity.this, MainActivity.class);
        navigateToOfferListIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(navigateToOfferListIntent);
    }

    private void NavigateToCreateUserProfileScreen() {
        Intent navigateToCreateUserProfileIntent = new Intent(AuthActivity.this, CreateUserProfileActivity.class);
        navigateToCreateUserProfileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(navigateToCreateUserProfileIntent);
    }

    private boolean ValidateUserInput() {
        final String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        boolean isValid = true;

        if (email.isEmpty()) {
            emailField.setError("Specificati o adresa de e-mail");
            isValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailField.setError("Adresa e-mail invalida");
            isValid = false;
        }

        if (password.isEmpty()) {
            passwordField.setError("Specificati o parola");
            isValid = false;
        } else if (password.length() < 6) {
            passwordField.setError("Parola trebuie sa contina minim 6 caractere");
            isValid = false;
        }

        return isValid;
    }

    private void initializeComponents() {
        emailField = findViewById(R.id.authActivity_emailField);
        passwordField = findViewById(R.id.authActivity_passwordField);
        errorTextMessage = findViewById(R.id.authActivity_errorText);
        loginButton = findViewById(R.id.login_button);

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email"));
    }

    public void onLoginWithFbClicked(View view) {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AuthCredential credential = FacebookAuthProvider.getCredential(loginResult.getAccessToken().getToken());
                authService.signInWithCredential(credential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {

                        userService.getCurrentUser().addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                User user = task1.getResult().toObject(User.class);

                                if (user == null) {
                                    saveUserToDatabase(task.getResult().getUser().getEmail(), task.getResult().getUser().getUid());
                                    NavigateToCreateUserProfileScreen();
                                    return;
                                }

                                if (user.getUserProfile() == null) {
                                    NavigateToCreateUserProfileScreen();
                                } else {
                                    NavigateToMainScreen();
                                }
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }


}
