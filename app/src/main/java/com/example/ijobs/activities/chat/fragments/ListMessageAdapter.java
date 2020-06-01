package com.example.ijobs.activities.chat.fragments;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ijobs.R;
import com.example.ijobs.activities.chat.SendMessageActivity;
import com.example.ijobs.activities.jobSeeker.CreateJobSeekerProfileActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListMessageAdapter extends RecyclerView.Adapter<ListMessageAdapter.ListMessageViewHolder> {

    private HashMap<String, String> userIdToNameMapping;
    private String currentUserId;
    private Context context;
    private HashMap<String, String> latestChats;

    public ListMessageAdapter(Context context, HashMap<String, String> latestChats, HashMap<String, String> userIdToNameMapping, String currentUserId) {
        this.context = context;
        this.latestChats = latestChats;
        this.userIdToNameMapping = userIdToNameMapping;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public ListMessageAdapter.ListMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_message_item, parent, false);
        return new ListMessageAdapter.ListMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMessageAdapter.ListMessageViewHolder holder, int position) {
        String username = (new ArrayList<>(latestChats.keySet())).get(position);
        String chatPreview = (new ArrayList<>(latestChats.values())).get(position);

        holder.username.setText(userIdToNameMapping.get(username));
        holder.chatPreview.setText(chatPreview);

        holder.itemView.setOnClickListener(v -> {
            Intent sendMessageActivity = new Intent(holder.itemView.getContext(), SendMessageActivity.class);
            sendMessageActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            sendMessageActivity.putExtra("userToChatWithId", username);
            sendMessageActivity.putExtra("userId", currentUserId);
            context.getApplicationContext().startActivity(sendMessageActivity);
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return latestChats.size();
    }

    public class ListMessageViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView chatPreview;

        public ListMessageViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.list_message_item_username);
            chatPreview = itemView.findViewById(R.id.list_message_item_chat_preview);
        }
    }
}