package com.example.uberclone.providers;

import com.example.uberclone.Models.Client;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ClientProvider {
    DatabaseReference mDatabase;

    public ClientProvider() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child("Clients");
    }
    public Task<Void> create(Client client){
        return mDatabase.child(client.getId()).setValue(client);
    }
}
