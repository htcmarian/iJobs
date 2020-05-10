package com.example.ijobs.fragments.createJobSeeker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ijobs.CreateJobSeekerProfileActivity;
import com.example.ijobs.R;

import java.util.Date;

public class IntroBirthdateFragment extends Fragment {

    private EditText dateInput;
    private EditText nameInput;
    private Button nextButton;
    private DatePickerDialog.OnDateSetListener datePickerListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_job_seeker_profile_intro_birthdate, container, false);

        initializeComponents(view);

        return view;
    }

    private void initializeComponents(View view) {
        dateInput = view.findViewById(R.id.create_job_seeker_profile_birthDateInput);
        nameInput = view.findViewById(R.id.create_job_seeker_profile_nameInput);
        nextButton = view.findViewById(R.id.create_job_seeker_profile_nextButton);

        dateInput.setRawInputType(InputType.TYPE_NULL);
        dateInput.setFocusable(true);

        dateInput.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog_MinWidth, datePickerListener, 1980, 1, 1);
            datePickerDialog.show();
        });

        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isValidInput = validateInput();
                nextButton.setEnabled(isValidInput);
                ((CreateJobSeekerProfileActivity) getActivity()).setFormName(nameInput.getText().toString());
            }
        });

        datePickerListener = (view1, year, month, dayOfMonth) -> {
            ((CreateJobSeekerProfileActivity) getActivity()).setFormBirthdate(new Date(year, month, dayOfMonth));
            dateInput.setText((dayOfMonth < 10 ? "0" : "") + dayOfMonth + "/" + (month < 10 ? "0" : "") + month + "/" + year);

            boolean isValidInput = validateInput();

            nextButton.setEnabled(isValidInput);
        };

        nextButton.setOnClickListener(v -> ((CreateJobSeekerProfileActivity) getActivity()).goToStep(CreateJobSeekerSteps.IntroAddressFragment));
    }

    private boolean validateInput(){
        boolean isValid = true;

        String inputDate = dateInput.getText().toString();
        String name = dateInput.getText().toString();

        if(inputDate.isEmpty()){
            dateInput.setError("Nu ati specificat data nasterii");
            isValid = false;
        }

        if(name.isEmpty()){
            nameInput.setError("Nu ati specificat anul nasterii");
            isValid = false;
        }

        return isValid;
    }
}
