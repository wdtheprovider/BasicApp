package com.wdtheprovider.basicapp.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;
import java.util.HashMap;

public class DBHelper {

    private final DatabaseReference databaseReference;

    public DBHelper() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Jobs.class.getSimpleName());
    }

    public Task<Void> AddPost(Jobs job, String key) {
        //return databaseReference.push().setValue(job);
        return  databaseReference.child(key).setValue(job);
    }

    public Task<Void> UpdateJob(String key, HashMap<String,Object> hashMap){
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> RemoveJob(String key){
      return databaseReference.child(key).removeValue();
    }
}
