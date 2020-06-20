package com.groupforyouapp.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.groupforyouapp.Models.Groups;
import com.groupforyouapp.Models.Users;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taras Zhygal on 7-May-20.
 */

public class FirebasedbHelper {
    // public void updateData(String nameGroup, String author, String number,String description,String category,byte[] image, int id) {
    private FirebaseDatabase mDb;
    private DatabaseReference mGroupDbRef;
    private DatabaseReference mUserRef;
    private List<Groups> groups = new ArrayList<>();

    public FirebasedbHelper () {
        mDb = FirebaseDatabase.getInstance();
        mGroupDbRef= mDb.getReference("Groups");
        mUserRef = mDb.getReference("Users");
    }

    public void getGroups(final MyCallback myCallback) {
        mGroupDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groups.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        Groups group= snapshot.getValue(Groups.class);
                        groups.add(group);
                }
                myCallback.onSuccess(groups);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("Tag", databaseError.getMessage());
            }
        });
    }

    public void searchGroups(final String queryText, final MyCallback myCallback){
        Query query = mGroupDbRef.orderByChild("nameGroup").startAt(queryText)
                .endAt(queryText+"\uf8ff");;
        query.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                groups.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Groups group= snapshot.getValue(Groups.class);
                    groups.add(group);
                }
                myCallback.onSuccess(groups);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                Log.d("Tag", databaseError.getMessage());
            }
        });
    }

    public void addGroup(Groups group){
        String key = mGroupDbRef.push().getKey();
        mGroupDbRef.child(key).setValue(group)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
    }

    public void getCurUser(final MyCallback myCallback){
        final String uID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        final List<Users> user = new ArrayList<>();
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.add(dataSnapshot.child(uID).getValue(Users.class));
                myCallback.onSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}