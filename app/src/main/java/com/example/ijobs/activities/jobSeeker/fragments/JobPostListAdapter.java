package com.example.ijobs.activities.jobSeeker.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ijobs.R;
import com.example.ijobs.viewmodels.JobPostViewModel;

import java.util.ArrayList;

public class JobPostListAdapter extends ArrayAdapter<JobPostViewModel> {

    private final Context context;
    private final ArrayList<JobPostViewModel> values;

    public JobPostListAdapter(Context context, int textViewResourceId, ArrayList<JobPostViewModel> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.job_seeker_list_job_list_item, parent, false);

        TextView jobTitleTextView = rowView.findViewById(R.id.job_seeker_list_item_jobTitleText);
        TextView jobTypeTextView = rowView.findViewById(R.id.job_seeker_list_item_jobTypeText);
        TextView jobLocationTextView = rowView.findViewById(R.id.job_seeker_list_item_jobLocationText);


        jobTitleTextView.setText(values.get(position).getJobDescription());
        jobTypeTextView.setText(values.get(position).getJobType());
        jobLocationTextView.setText(values.get(position).getCity());

        return rowView;
    }
}
