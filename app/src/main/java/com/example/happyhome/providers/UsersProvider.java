package com.example.happyhome.providers;

import com.example.happyhome.models.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UsersProvider {

    private CollectionReference mColletion; /*Se crea metodo construstor*/

    public UsersProvider() {
        mColletion=FirebaseFirestore.getInstance().collection("Users");
    }
    public Task<DocumentSnapshot> getUser(String id){
        return mColletion.document(id).get();
    }
    public Task<Void> create(User user){
        return mColletion.document(user.getId()).set(user);
    }
    public Task<Void> update(User user){
        Map<String,Object> map=new HashMap<>();
        map.put("email", user.getEmail());
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        return mColletion.document(user.getId()).update(map);
    }

}
