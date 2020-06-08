package com.example.ijobs.activities.jobSeeker.fragments;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ijobs.R;
import com.example.ijobs.services.ImageProvider;
import com.example.ijobs.viewmodels.JobPostViewModel;

import java.util.List;

public class JobSeekerListAdapter extends RecyclerView.Adapter<JobSeekerListAdapter.JobSeekerListItemViewHolder> {

    private Context context;
    private List<JobPostViewModel> jobPosts;

    public JobSeekerListAdapter(Context context, List<JobPostViewModel> jobPosts) {
        this.context = context;
        this.jobPosts = jobPosts;
    }

    @NonNull
    @Override
    public JobSeekerListAdapter.JobSeekerListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_seeker_list_job_list_item, parent, false);
        return new JobSeekerListAdapter.JobSeekerListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobSeekerListAdapter.JobSeekerListItemViewHolder holder, int position) {
        JobPostViewModel job = jobPosts.get(position);

        holder.jobTitleTextView.setText(job.getJobDescription());
        holder.jobTypeTextView.setText(job.getJobType());
        holder.jobLocationTextView.setText(job.getCity());
        holder.jobServiceRequiredImageView.setImageURI(ImageProvider.getImageUri(job.getServiceRequiredImageThumbnail()));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return jobPosts.size();
    }

    public class JobSeekerListItemViewHolder extends RecyclerView.ViewHolder {
        public TextView jobTitleTextView;
        public TextView jobTypeTextView;
        public TextView jobLocationTextView;
        public ImageView jobServiceRequiredImageView;

        public JobSeekerListItemViewHolder(View itemView) {
            super(itemView);

            jobTitleTextView = itemView.findViewById(R.id.job_seeker_list_item_jobTitleText);
            jobTypeTextView = itemView.findViewById(R.id.job_seeker_list_item_jobTypeText);
            jobLocationTextView = itemView.findViewById(R.id.job_seeker_list_item_jobLocationText);
            jobServiceRequiredImageView = itemView.findViewById(R.id.job_seeker_list_item_jobImage);
        }
    }
}

