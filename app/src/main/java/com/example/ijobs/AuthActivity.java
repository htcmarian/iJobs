package com.example.ijobs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ijobs.services.AuthService;
import com.example.ijobs.services.UserService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private TextView errorTextMessage;

    private AuthService authService;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initializeComponents();

        authService = new AuthService();
        userService = new UserService();
    }

    public void onLoginButtonClick(View view) {
        boolean isUserInputValid = ValidateUserInput();

        if (isUserInputValid) {
            final String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            authService
                    .login(email, password)
                    .addOnSuccessListener(authResult -> NavigateToOfferListScreen())
                    .addOnFailureListener(e -> {
                        Log.e("EROARE", e.getMessage());
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

                        userService
                                .createUser(userId, email)
                                .addOnSuccessListener(aVoid -> NavigateToOfferListScreen())
                                .addOnFailureListener(e -> errorTextMessage.setText("A intervenit o eroare. Incercati din nou"));
                    })
                    .addOnFailureListener(e -> {
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            errorTextMessage.setText("Exista deja un cont asociat email-ului");
                        }
                    });
        }
    }

    private void NavigateToOfferListScreen() {
        Intent navigateToOfferListIntent = new Intent(AuthActivity.this, MainActivity.class);
        navigateToOfferListIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(navigateToOfferListIntent);
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
    }

}
