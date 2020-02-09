package com.mtaufiqramadhan.chataja.ui.contact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mtaufiqramadhan.chataja.R;
import com.mtaufiqramadhan.chataja.common.OnItemClickCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<ContactModel> modelList;
    private OnItemClickCallback onitemClickCallback;

    ContactAdapter(List<ContactModel> modelList) {
        this.modelList = modelList;
    }

    void setOnitemClickCallback(OnItemClickCallback onitemClickCallback) {
        this.onitemClickCallback = onitemClickCallback;
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        holder.bind(holder.itemView, modelList.get(position));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private TextView mName, mEmail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.img_contact_user);
            mName = itemView.findViewById(R.id.tv_contact_name);
            mEmail = itemView.findViewById(R.id.tv_contact_email);
        }

        void bind(View itemView, ContactModel contactModel) {
            Glide.with(itemView.getContext())
                    .load(contactModel.getAvatar())
                    .into(mImage);
            mName.setText(contactModel.getName());
            mEmail.setText(contactModel.getEmail());

            itemView.setOnClickListener(view -> onitemClickCallback.OnItemClicked(modelList.get(getAdapterPosition())));
        }

    }
}
