package com.mtaufiqramadhan.chataja.ui.chat;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mtaufiqramadhan.chataja.R;
import com.mtaufiqramadhan.chataja.base.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatRoomActivity extends BaseActivity {

    private List<ChatRoomModel> modelList;
    private ChatRoomViewModel chatRoomViewModel;

    private RecyclerView recyclerView;
    private ChatRoomAdapter chatRoomAdapter;

    private EditText mText;
    private ImageView mImage;
    private TextView mName, mEmail;

    private String avatar, name, email, receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        setupFindViews();
    }

    @Override
    public void setupFindViews() {
        modelList = new ArrayList<>();
        chatRoomViewModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        recyclerView = findViewById(R.id.rv_chat_list);
        mText = findViewById(R.id.edt_message_input);
        mImage = findViewById(R.id.img_chat_user);
        mName = findViewById(R.id.tv_chat_name);
        mEmail = findViewById(R.id.tv_chat_email);

        avatar = getIntent().getStringExtra("avatar");
        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        receiver = getIntent().getStringExtra("receiver");

        setupListData();
        setupListener();
    }

    @Override
    public void setupListData() {
        Glide.with(this)
                .load(avatar)
                .into(mImage);
        mName.setText(name);
        mEmail.setText(email);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        chatRoomAdapter = new ChatRoomAdapter(modelList);
        recyclerView.setAdapter(chatRoomAdapter);

        chatRoomViewModel.getData(mAuth.getUid(), receiver).observe(this, chatRoomModels -> {
            modelList.clear();
            modelList.addAll(chatRoomModels);

            chatRoomAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(modelList.size() - 1);
        });
    }

    @Override
    public void setupListener() {
        findViewById(R.id.btn_chat_back).setOnClickListener(view -> finish());
        findViewById(R.id.btn_send_message).setOnClickListener(view -> {
            String message = mText.getText().toString();
            if (!message.equals("")) {
                sendMessage(mAuth.getUid(), message);
            } else {
                Toast.makeText(this, "You can't send empty message", Toast.LENGTH_SHORT).show();
            }
            mText.setText("");
        });
    }

    private void sendMessage(String sender, String message) {
        Date currentTime = Calendar.getInstance().getTime();

        HashMap<String, Object> hashMapMsg = new HashMap<>();
        hashMapMsg.put("sender", sender);
        hashMapMsg.put("message", message);
        hashMapMsg.put("timestamp", currentTime);

        db.collection("message")
                .add(hashMapMsg)
                .addOnSuccessListener(reference ->
                        chatRoomAdapter.notifyDataSetChanged()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(ChatRoomActivity.this, "Error send Message", Toast.LENGTH_LONG).show()
                );

    }

}
