package com.example.ijobs.activities.chat.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ijobs.R;
import com.example.ijobs.data.Chat;

import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder> {

    private String currentUserId;
    private Context context;
    private List<Chat> chats;

    private static int MESSAGE_RECEIVED = 0;
    private static int MESSAGE_SENT = 1;

    public ChatMessageAdapter(Context context, List<Chat> chats, String currentUserId) {
        this.context = context;
        this.chats = chats;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public ChatMessageAdapter.ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int messageLayoutToShow = viewType == MESSAGE_SENT ? R.layout.chat_item_send : R.layout.chat_item_receive;

        View view = LayoutInflater.from(context).inflate(messageLayoutToShow, parent, false);
        return new ChatMessageAdapter.ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageAdapter.ChatViewHolder holder, int position) {
        Chat chat = chats.get(position);

        holder.messageToDisplay.setText(chat.getMessage());
    }

    @Override
    public int getItemViewType(int position) {
        if (chats.get(position).getSender().equals(currentUserId)) {
            return MESSAGE_SENT;
        }

        return MESSAGE_RECEIVED;
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView messageToDisplay;

        public ChatViewHolder(View itemView) {
            super(itemView);

            messageToDisplay = itemView.findViewById(R.id.send_message_show_message);
        }
    }
}
