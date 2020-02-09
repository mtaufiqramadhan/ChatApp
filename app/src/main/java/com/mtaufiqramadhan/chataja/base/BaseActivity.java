package com.mtaufiqramadhan.chataja.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mtaufiqramadhan.chataja.ui.LoginActivity;
import com.mtaufiqramadhan.chataja.ui.chat.ChatRoomActivity;
import com.mtaufiqramadhan.chataja.ui.contact.ContactActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    public static FirebaseAuth mAuth;
    public static FirebaseUser currentUser;

    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
    }

    public abstract void setupFindViews();

    public abstract void setupListData();

    public abstract void setupListener();

    public void updateUI(FirebaseUser currentUser) {
        if (currentUser == null) {
            goToLoginActivity();
        } else {
            goToContactActivity();
        }
    }

    public void goToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void goToContactActivity() {
        startActivity(new Intent(this, ContactActivity.class));
    }

    public void goToChatRoomActivity(String id, String avatar, String name, String email) {
        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra("receiver", id);
        intent.putExtra("avatar", avatar);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        startActivity(intent);
    }

}
