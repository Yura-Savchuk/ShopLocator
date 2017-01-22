package com.example.shoplocator.data.firebaseDb;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 21.01.17.
 */

public class ValueEventListenerAdapter implements ValueEventListener {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {}

    @Override
    public void onCancelled(DatabaseError databaseError) {}
}