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

public class IntroAddressFragment extends Fragment {

    private EditText addressField, cityField, postalCodeField;
    private Button nextButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_user_profile_intro_address, container, false);

        initializeComponents(view);

        return view;
    }

    private void initializeComponents(View view) {
        addressField = view.findViewById(R.id.create_user_profile_addressLocationTextInput);
        cityField = view.findViewById(R.id.create_user_profile_addressCityTextInput);
        postalCodeField = view.findViewById(R.id.create_user_profile_addressPostCodeTextInput);
        nextButton = view.findViewById(R.id.create_user_profile_nextButton);

        addressField.addTextChangedListener(onTextChanged());
        cityField.addTextChangedListener(onTextChanged());
        postalCodeField.addTextChangedListener(onTextChanged());

        nextButton.setOnClickListener(v -> {

            CreateUserProfileActivity activity = ((CreateUserProfileActivity) getActivity());

            activity.setFormAddress(addressField.getText().toString(), cityField.getText().toString(), postalCodeField.getText().toString());

            activity.goToStep(CreateUserProfileSteps.IntroPhoneNumberFragment);
        });
    }

    private TextWatcher onTextChanged() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean inputIsValid = ValidateInput();

                nextButton.setEnabled(inputIsValid);
            }
        };
    }

    private boolean ValidateInput() {
        boolean isValid = true;

        if (addressField.getText().toString().isEmpty()) {
            addressField.setError("Specificati o adresa");
            isValid = false;
        }
        if (cityField.getText().toString().isEmpty()) {
            cityField.setError("Specificati un oras");
            isValid = false;
        }
        if (postalCodeField.getText().toString().isEmpty()) {
            postalCodeField.setError("Specificati un cod postal");
            isValid = false;
        }

        return isValid;
    }
}
