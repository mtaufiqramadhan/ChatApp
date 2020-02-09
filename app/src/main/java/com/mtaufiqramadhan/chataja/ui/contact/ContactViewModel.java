package com.mtaufiqramadhan.chataja.ui.contact;

import android.os.Handler;
import android.os.Looper;

import com.mtaufiqramadhan.chataja.common.FirebaseListener;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.mtaufiqramadhan.chataja.base.BaseActivity.db;
import static com.mtaufiqramadhan.chataja.base.BaseActivity.mAuth;

public class ContactViewModel extends ViewModel {

    private MutableLiveData<List<ContactModel>> data;
    private List<ContactModel> modelList;

    public ContactViewModel() {
        data = new MutableLiveData<>();
        modelList = new ArrayList<>();
    }

    private void registerAllData(FirebaseListener listener) {
        db.collection("user/" + mAuth.getUid() + "/contact").get()
                .addOnSuccessListener(documentSnapshots -> {
                    for (int i = 0; i < documentSnapshots.size(); i++) {
                        db.collection("user").document(String.valueOf(documentSnapshots.getDocuments().get(i).get("contactId"))).get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (!documentSnapshot.exists()) {
                                        new Handler(Looper.getMainLooper()).post(() -> listener.onError("Data is Empty!"));
                                    } else {
                                        new Handler(Looper.getMainLooper()).post(listener::onSuccess);
                                        modelList.add(documentSnapshot.toObject(ContactModel.class));
                                        data.setValue(modelList);
                                    }
                                });
                    }
                });
    }

    LiveData<List<ContactModel>> getData(FirebaseListener listener) {
        registerAllData(listener);
        return data;
    }

}
