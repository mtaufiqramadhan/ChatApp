package com.mtaufiqramadhan.chataja.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mtaufiqramadhan.chataja.R;
import com.mtaufiqramadhan.chataja.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    private EditText mEmail, mPassword;
    private ProgressBar mLoading;
    private Button mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupFindViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void setupFindViews() {
        mEmail = findViewById(R.id.edt_email);
        mPassword = findViewById(R.id.edt_password);
        mLoading = findViewById(R.id.pb_loading);
        mLogin = findViewById(R.id.btn_login);

        setupListData();
        setupListener();
    }

    @Override
    public void setupListData() {
        //TODO
    }

    @Override
    public void setupListener() {
        mLogin.setOnClickListener(view -> {
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();

            setupEnabled(false);

            if (email.isEmpty()) {
                mEmail.setError("Empty");
                setupEnabled(true);
                return;
            }

            if (password.isEmpty()) {
                mPassword.setError("Empty");
                setupEnabled(true);
                return;
            }

            signIn(email, password);
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        updateUI(mAuth.getCurrentUser());
                        setupEnabled(true);
                    } else {
                        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        setupEnabled(true);
                    }
                });
    }

    private void setupEnabled(boolean status) {
        if (status) {
            mLoading.setVisibility(View.GONE);

            mEmail.setEnabled(true);
            mPassword.setEnabled(true);
            mLogin.setEnabled(true);
        } else {
            mLoading.setVisibility(View.VISIBLE);

            mEmail.setEnabled(false);
            mPassword.setEnabled(false);
            mLogin.setEnabled(false);
        }
    }

}
