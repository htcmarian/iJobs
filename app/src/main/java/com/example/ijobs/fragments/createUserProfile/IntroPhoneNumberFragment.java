package com.example.ijobs.fragments.createUserProfile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ijobs.CreateUserProfileActivity;
import com.example.ijobs.R;

public class IntroPhoneNumberFragment extends Fragment {

    private EditText phoneNumberField;
    private Button nextButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_user_profile_intro_telephone, container, false);

        initializeComponents(view);

        return view;
    }

    private void initializeComponents(View view) {
        phoneNumberField = view.findViewById(R.id.create_user_profile_phoneNumberInput);
        nextButton = view.findViewById(R.id.create_user_profile_nextButton);

        phoneNumberField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isInputValid = ValidateInput();
                nextButton.setEnabled(isInputValid);
            }
        });

        nextButton.setOnClickListener(v -> {
            CreateUserProfileActivity activity = (CreateUserProfileActivity) getActivity();
            activity.setFormPhoneNumber(phoneNumberField.getText().toString());

            ((CreateUserProfileActivity) getActivity()).goToStep(CreateUserProfileSteps.IntroProfilePictureFragment);
        });
    }

    private boolean ValidateInput() {
        boolean isValid = true;

        if (phoneNumberField.getText().toString().isEmpty()) {
            phoneNumberField.setError("Specificati un numar de telefon");
            isValid = false;
        }

        return isValid;
    }
}
