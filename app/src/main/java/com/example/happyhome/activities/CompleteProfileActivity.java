package com.example.happyhome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.happyhome.R;
import com.example.happyhome.models.User;
import com.example.happyhome.providers.AutProviders;
import com.example.happyhome.providers.UsersProvider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CompleteProfileActivity extends AppCompatActivity {
    TextInputEditText mTextInputUsername;
    Button mButtonRegisterC;
    //FirebaseAuth mAut; /*Se modifica el metodo de autenticación*/
    //FirebaseFirestore mFirestore; /*Se modifica el metodo de autenticación*/
    AutProviders mAutProviders;
    UsersProvider mUsersProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);

        mTextInputUsername=findViewById(R.id.textInputEditTextUsernameC);
        mButtonRegisterC=findViewById(R.id.ButtonRegisterC);

        mAutProviders= new AutProviders();
        mUsersProvider = new UsersProvider();
        
        mButtonRegisterC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        String username=mTextInputUsername.getText().toString();

        if (!username.isEmpty()){
            updateUser(username);
        }else {
            Toast.makeText(this, "Para continuar inserte el nombre del usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser(String username) {
        String id=mAutProviders.getUid();
        User user=new User();
        user.setUsername(username);
        user.setId(id);
        //Map<String, Object>map=new HashMap<>();
        //map.put("username",username);
        mUsersProvider.update(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(CompleteProfileActivity.this,HomeActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(CompleteProfileActivity.this, "No se almacenó el usuario en la base de datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}