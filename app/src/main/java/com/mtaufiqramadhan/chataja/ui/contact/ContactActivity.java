package com.mtaufiqramadhan.chataja.ui.contact;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mtaufiqramadhan.chataja.R;
import com.mtaufiqramadhan.chataja.base.BaseActivity;
import com.mtaufiqramadhan.chataja.common.FirebaseListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ContactActivity extends BaseActivity implements FirebaseListener {

    private List<ContactModel> modelList;
    private ContactViewModel contactViewModel;

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setupFindViews();
    }

    @Override
    public void setupFindViews() {
        modelList = new ArrayList<>();
        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        recyclerView = findViewById(R.id.rv_contact);
        progressBar = findViewById(R.id.pb_loading);

        setupListData();
        setupListener();
    }

    @Override
    public void setupListData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(modelList);
        recyclerView.setAdapter(contactAdapter);

        contactViewModel.getData(this).observe(this, contactModels -> {
            modelList.clear();
            modelList.addAll(contactModels);

            contactAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public void setupListener() {
        findViewById(R.id.btn_logout).setOnClickListener(view -> logout());
        contactAdapter.setOnitemClickCallback(contactModel ->
                goToChatRoomActivity(contactModel.getId(), contactModel.getAvatar(), contactModel.getName(), contactModel.getEmail())
        );
    }

    @Override
    public void onSuccess() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }

    private void logout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Notification");
        alertDialogBuilder
                .setMessage("Are you sure logout from account?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNegativeButton("No", (dialog, id) -> dialog.cancel())
                .setPositiveButton("Yes", (dialog, id) -> {
                    mAuth.signOut();
                    goToLoginActivity();
                    finish();
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
