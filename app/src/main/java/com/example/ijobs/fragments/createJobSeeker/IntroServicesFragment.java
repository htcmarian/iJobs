package com.example.ijobs.fragments.createJobSeeker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ijobs.CreateJobSeekerProfileActivity;
import com.example.ijobs.R;
import com.example.ijobs.data.ServicesOffered;
import com.example.ijobs.viewmodels.ServiceOfferListItemViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntroServicesFragment extends Fragment {
    private ListView servicesList;
    private Button nextButton;
    private ServiceOfferListItemViewModel[] listItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_job_seeker_profile_intro_services, container, false);

        listItems = generateListData();

        initializeComponents(view);

        return view;
    }

    private void initializeComponents(View view) {
        nextButton = view.findViewById(R.id.create_job_seeker_profile_nextButton);

        nextButton.setOnClickListener(v -> {
            CreateJobSeekerProfileActivity activity = (CreateJobSeekerProfileActivity) getActivity();
            activity.setFormServicesOffered(getSelectedServices());
            activity.finalizeProfile();
        });

        servicesList = view.findViewById(R.id.create_job_seeker_profile_services_list);

        IntroServicesListAdapter adapter = new IntroServicesListAdapter(getContext(), R.id.create_job_seeker_profile_service_item_text, listItems);
        servicesList.setAdapter(adapter);

        servicesList.setOnItemClickListener((parent, view1, position, id) -> {
            ServiceOfferListItemViewModel itemSelected = listItems[position];
            itemSelected.setSelected(!itemSelected.isSelected());

            ImageView selectedImage = view1.findViewById(R.id.create_job_seeker_profile_service_item_image);
            selectedImage.setVisibility(itemSelected.isSelected() ? View.VISIBLE : View.INVISIBLE);

            boolean anyServicesSelected = getSelectedServices().size() > 0;
            nextButton.setEnabled(anyServicesSelected);
        });
    }

    private ServiceOfferListItemViewModel[] generateListData() {
        ServiceOfferListItemViewModel[] list = new ServiceOfferListItemViewModel[ServicesOffered.List.size()];

        for (int i = 0; i < ServicesOffered.List.size(); i++) {
            list[i] = new ServiceOfferListItemViewModel(ServicesOffered.List.get(i), false);
        }

        return list;
    }

    private List<ServiceOfferListItemViewModel> getSelectedServices() {
        return Arrays.stream(listItems).filter(x -> x.isSelected()).collect(Collectors.toList());
    }
}
