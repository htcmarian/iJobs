package com.example.ijobs.activities.jobSeeker.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ijobs.R;
import com.example.ijobs.viewmodels.ServiceOfferListItemViewModel;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IntroServicesListAdapter extends ArrayAdapter<ServiceOfferListItemViewModel> {
    private final Context context;
    private final ServiceOfferListItemViewModel[] values;

    public IntroServicesListAdapter(Context context, int textViewResourceId, ServiceOfferListItemViewModel[] values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.create_user_profile_intro_services_listitem, parent, false);
        TextView textView = rowView.findViewById(R.id.create_user_profile_service_item_text);
        ImageView selectedImage = rowView.findViewById(R.id.create_user_profile_service_item_image);

        textView.setText(values[position].getServiceName());

        rowView.setOnClickListener(v -> {
            values[position].setSelected(!values[position].isSelected());
            selectedImage.setVisibility(values[position].isSelected() ? View.VISIBLE : View.INVISIBLE);
        });

        return rowView;
    }

    public List<String> getSelectedValues(){
        return Arrays.stream(values).filter(x ->x.isSelected()).map(x->x.getServiceName()).collect(Collectors.toList());
    }
}
