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

import com.example.ijobs.repositories.UserRepository;
import com.example.ijobs.services.AuthService;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private TextView errorTextMessage;

    private AuthService authService;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        initializeComponents();

        authService = new AuthService();
        userRepository = new UserRepository();
    }

    public void onLoginButtonClick(View view) {
        boolean isUserInputValid = ValidateUserInput();

        if (isUserInputValid) {
            final String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            authService.login(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    NavigateToOfferListScreen();
                }
            });
        }
    }

    public void onRegisterButtonClick(View view) {
        boolean isUserInputValid = ValidateUserInput();

        if (isUserInputValid) {
            final String email = emailField.getText().toString();
            String password = passwordField.getText().toString();

            authService.register(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = authService.getUser();
                            String userId = user.getUid();

                            userRepository.createUser(userId, email).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    NavigateToOfferListScreen();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    errorTextMessage.setText("A intervenit o eroare. Incercati din nou");
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthUserCollisionException) {
                                errorTextMessage.setText("Exista deja un cont asociat email-ului");
                            }
                        }
                    });
        }
    }

    private void NavigateToOfferListScreen() {
        Intent navigateToOfferListIntent = new Intent(AuthActivity.this, OfferListActivity.class);
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
