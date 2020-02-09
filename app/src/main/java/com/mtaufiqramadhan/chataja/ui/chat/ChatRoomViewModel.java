package com.mtaufiqramadhan.chataja.ui.chat;

import com.google.firebase.firestore.Query;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.mtaufiqramadhan.chataja.base.BaseActivity.db;

public class ChatRoomViewModel extends ViewModel {

    private MutableLiveData<List<ChatRoomModel>> data;

    public ChatRoomViewModel() {
        data = new MutableLiveData<>();
    }

    private void registerAllData(String sender, String receiver) {
        Query query = db.collection("message")
                .orderBy("timestamp", Query.Direction.ASCENDING);
        query.whereEqualTo("sender", sender);
        query.whereEqualTo("receiver", receiver);
        query.addSnapshotListener((documentSnapshots, e) -> {
            if (documentSnapshots != null) {
                data.setValue(documentSnapshots.toObjects(ChatRoomModel.class));
            }
        });
    }

    LiveData<List<ChatRoomModel>> getData(String sender, String receiver) {
        registerAllData(sender, receiver);
        return data;
    }

}
