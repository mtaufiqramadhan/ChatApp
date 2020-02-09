package com.mtaufiqramadhan.chataja.ui.chat;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mtaufiqramadhan.chataja.R;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.mtaufiqramadhan.chataja.base.BaseActivity.mAuth;

public class ChatRoomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ChatRoomModel> modelList;

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    ChatRoomAdapter(List<ChatRoomModel> modelList) {
        this.modelList = modelList;
    }

    public int getItemViewType(int position) {
        ChatRoomModel messages = modelList.get(position);

        if (messages.getSender().equals(mAuth.getUid())) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_MESSAGE_SENT:
                return new ChatSenderViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_send, parent, false)
                );
            case VIEW_TYPE_MESSAGE_RECEIVED:
                return new ChatReceivedViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_received, parent, false)
                );
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ChatSenderViewHolder chatSenderViewHolder = (ChatSenderViewHolder) holder;
                chatSenderViewHolder.bind(modelList.get(position));
                break;

            case VIEW_TYPE_MESSAGE_RECEIVED:
                ChatReceivedViewHolder chatReceivedViewHolder = (ChatReceivedViewHolder) holder;
                chatReceivedViewHolder.bind(modelList.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ChatSenderViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMessage, tvTime;

        ChatSenderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_sender_message);
            tvTime = itemView.findViewById(R.id.tv_sender_time);
        }

        @SuppressLint("SimpleDateFormat")
        void bind(ChatRoomModel chatRoomModel) {
            tvMessage.setText(chatRoomModel.getMessage());
            tvTime.setText(new SimpleDateFormat("dd-MM-YYYY (hh:mm:ss)").format(chatRoomModel.getTimestamp()));
        }
    }

    class ChatReceivedViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMessage, tvTime;

        ChatReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_received_message);
            tvTime = itemView.findViewById(R.id.tv_received_time);
        }

        @SuppressLint("SimpleDateFormat")
        void bind(ChatRoomModel chatRoomModel) {
            tvMessage.setText(chatRoomModel.getMessage());
            tvTime.setText(new SimpleDateFormat("dd-MM-YYYY (hh:mm:ss)").format(chatRoomModel.getTimestamp()));
        }
    }

}
